package com.ygs.netronic.repositories.impl;

import android.content.Context;
import android.database.SQLException;
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
import com.ygs.netronic.models.network.response.UserResponseModel;
import com.ygs.netronic.models.ui.UserRowModel;
import com.ygs.netronic.repositories.interfaces.RandomUsersRepository;
import com.ygs.netronic.repositories.interfaces.UserApiRepository;

import androidx.annotation.NonNull;
import io.reactivex.observers.DisposableCompletableObserver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLDataException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public final class RandomUsersRepositoryImpl implements RandomUsersRepository {

    private static final RandomUsersRepositoryImpl mInstance = new RandomUsersRepositoryImpl();
    private final UserApiRepository mRepository = new UserApiRepositoryImpl();
    private final AtomicBoolean mInUpdate = new AtomicBoolean(false);
    MutableLiveData<List<UserRowModel>> mData = new MutableLiveData<>();
    private Executor executor = (runnable) -> new Thread(runnable).start();

    private AppDatabase mDatabase;
    private ConnectivityManager mManager;


    private RandomUsersRepositoryImpl() {
    }

    @Override
    public LiveData<List<UserRowModel>> getUsersList(int usersCount) {

       runInCompletable(() -> {
            if (!mInUpdate.get()) {
                mInUpdate.set(true);
                updateDatabase(mDatabase, usersCount);
                List<UserRowSample> samples = mDatabase.userDao().selectRowSamples().blockingGet();
                List<UserRowModel> rows = samples.stream().map(UserRowSample::mapToUserRowModel).collect(Collectors.toList());
                mData.postValue(rows);
                mInUpdate.compareAndSet(true, false);
            }
        });


        return mData;
    }

    @Override
    public LiveData<List<UserRowModel>> getOfflineUsersList() {
        runInCompletable(() -> {
            if (!mInUpdate.get()) {
                AppDatabase database = DatabaseManager.getInstance().getCurrentDatabase();
                List<UserRowSample> samples = database.userDao().selectRowSamples().blockingGet();
                List<UserRowModel> rows = samples.stream().map(UserRowSample::mapToUserRowModel).collect(Collectors.toList());
                mData.postValue(rows);
            }
        });

        return mData;
    }

    private UserResponseModel convertToResponseModel(JsonObject jsonObject) {
        return JsonResponseToInstanceConverter
                .convertToInstance(jsonObject, UserResponseModel.class);

    }


    private void updateDatabase(@NonNull final AppDatabase database, int usersCount) {
        if (isInternetAvailable()) {

            try {
                database.userDao().clear().blockingAwait();
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

        }

    }

    public static RandomUsersRepository getInstance(Context context) {


        if (DatabaseManager.getInstance().getCurrentDatabase() == null) {
            mInstance.mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            AppDatabase database = RandomUserDatabase.getInstance(context);
            DatabaseManager.getInstance().setCurrentDatabase(database);
            mInstance.mDatabase = database;
        }
        return mInstance;
    }

    private void runInCompletable(Runnable runnable) {
        Completable.fromRunnable(runnable)
                .subscribeOn(Schedulers.io())
                .subscribe();
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
