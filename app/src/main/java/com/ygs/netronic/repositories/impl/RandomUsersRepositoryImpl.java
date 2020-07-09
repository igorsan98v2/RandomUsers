package com.ygs.netronic.repositories.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ygs.netronic.annotations.NetworkType;
import com.ygs.netronic.converters.JsonResponseToInstanceConverter;
import com.ygs.netronic.database.AppDatabase;
import com.ygs.netronic.database.DatabaseManager;
import com.ygs.netronic.database.RandomUserDatabase;
import com.ygs.netronic.database.entities.Location;
import com.ygs.netronic.database.entities.PictureUrl;
import com.ygs.netronic.database.entities.User;
import com.ygs.netronic.database.samples.UserRowSample;
import com.ygs.netronic.interfaces.Foreign;
import com.ygs.netronic.interfaces.Local;
import com.ygs.netronic.models.network.response.UserResponseModel;
import com.ygs.netronic.models.ui.UserRowModel;
import com.ygs.netronic.repositories.interfaces.RandomUsersRepository;
import com.ygs.netronic.repositories.interfaces.UserApiRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Response;

public final class RandomUsersRepositoryImpl implements RandomUsersRepository {

    private static final RandomUsersRepositoryImpl mInstance = new RandomUsersRepositoryImpl();
    private final UserApiRepository mRepository = new UserApiRepositoryImpl();
    private Executor executor = (runnable) -> new Thread(runnable).start();
    private ConnectivityManager mManager;


    private RandomUsersRepositoryImpl() {
    }

    @Override
    public LiveData<List<UserRowModel>> getUsersList(int usersCount) {
        MutableLiveData<List<UserRowModel>> data = new MutableLiveData<>();

        AppDatabase database = DatabaseManager.getInstance().getCurrentDatabase();
        updateDatabase(database, usersCount);

        List<UserRowSample> samples = database.userDao().selectRowSamples().blockingGet();
        List<UserRowModel> rows = samples.stream().map(UserRowSample::mapToUserRowModel).collect(Collectors.toList());
        data.postValue(rows);
        return data;
    }

    @Override
    public LiveData<List<UserRowModel>> getOfflineUsersList() {
        MutableLiveData<List<UserRowModel>> data = new MutableLiveData<>();

        AppDatabase database = DatabaseManager.getInstance().getCurrentDatabase();

        List<UserRowSample> samples = database.userDao().selectRowSamples().blockingGet();
        List<UserRowModel> rows = samples.stream().map(UserRowSample::mapToUserRowModel).collect(Collectors.toList());
        data.postValue(rows);
        return data;
    }

    private UserResponseModel convertToResponseModel(JsonObject jsonObject) {
        return JsonResponseToInstanceConverter
                .convertToInstance(jsonObject, UserResponseModel.class);

    }



    private void updateDatabase(AppDatabase database, int usersCount) {
        if (isInternetAvailable()) {
            executor.execute(() -> {
                database.userDao().clear().blockingAwait();
                try {
                    List<User> users = new LinkedList<>();
                    List<Location> locations = new LinkedList();
                    List<PictureUrl> pictureUrls = new LinkedList();
                    Response<JsonElement> response = mRepository.getUsers(usersCount).execute();

                    if (response.isSuccessful() && response.body() instanceof JsonObject) {
                        UserResponseModel model = convertToResponseModel((JsonObject) response.body());
                        List<UserResponseModel.UserData> userDataList = model.userDataList;
                        userDataList.forEach(userData -> {
                            database.runInTransaction(() -> {
                                        User user = (User) userData.mapToLocal();
                                        long userId = database.userDao().insert(user).blockingGet();
                                        userData.location.appendToLocalList(userId, locations);
                                        userData.picture.appendToLocalList(userId, pictureUrls);

                                    }
                            );

                        });
                        database.locationDao().insert(locations).blockingGet();
                        database.pictureUrlDao().insert(pictureUrls).blockingGet();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static RandomUsersRepository getInstance(Context context) {


        if (DatabaseManager.getInstance().getCurrentDatabase() == null) {
            mInstance.mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            AppDatabase database = RandomUserDatabase.getInstance(context);
            DatabaseManager.getInstance().setCurrentDatabase(database);
        }
        return mInstance;
    }

    private boolean isInternetAvailable() {
        return mInstance.isInternetAvailable(NetworkType.CELLULAR) ||
                mInstance.isInternetAvailable(NetworkType.WIFI);
    }


    private boolean isInternetAvailable(int type) {
        Network[] networks = mManager.getAllNetworks();
        for (Network network : networks) {
            NetworkInfo info = mManager.getNetworkInfo(network);
            NetworkCapabilities capabilities = mManager.getNetworkCapabilities(network);
            if ((info != null) && info.isConnected() &&
                    (capabilities != null) && capabilities.hasTransport(type)) {
                return true;
            }
        }
        return false;
    }

}
