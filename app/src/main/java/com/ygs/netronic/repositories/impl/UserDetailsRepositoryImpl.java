package com.ygs.netronic.repositories.impl;

import com.ygs.netronic.annotations.ThrowableMessage;
import com.ygs.netronic.database.AppDatabase;
import com.ygs.netronic.database.DatabaseManager;
import com.ygs.netronic.database.samples.UserDetailsSample;
import com.ygs.netronic.models.ui.UserDetailsModel;
import com.ygs.netronic.repositories.interfaces.UserDetailsRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public final class UserDetailsRepositoryImpl implements UserDetailsRepository {
    private static final UserDetailsRepositoryImpl mInstance = new UserDetailsRepositoryImpl();
    private AppDatabase database;
    private MutableLiveData<UserDetailsModel> mModel = new MutableLiveData<>();

    @Override
    public LiveData<UserDetailsModel> getDetailsById(long userId) {

        database.userDao().selectDetailsSampleByUserId(userId)
                .map(UserDetailsSample::mapToUserDetailsModel)
                .doOnSuccess(userDetailsModel -> mModel.postValue(userDetailsModel))
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<UserDetailsModel>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }

                    @Override
                    public void onSuccess(UserDetailsModel model) {

                    }
                }).dispose();


        return mModel;
    }

    public static UserDetailsRepository getInstance() {

        mInstance.database = DatabaseManager.getInstance().getCurrentDatabase();
        if (mInstance.database == null) {

            throw new RuntimeException(ThrowableMessage.MSG_LOAD_RANDOM_USER_FRAGMENT_FIRST);
        }

        return mInstance;
    }
}
