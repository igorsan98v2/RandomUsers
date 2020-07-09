package com.ygs.netronic.viewmodels;

import android.view.View;

import com.ygs.netronic.annotations.RandomUserAnnotation;
import com.ygs.netronic.models.ui.UserRowModel;
import com.ygs.netronic.repositories.interfaces.RandomUsersRepository;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RandomUsersFragmentsViewModel extends ViewModel {

    private RandomUsersRepository mRepository;
    public LiveData<List<UserRowModel>> mUserRowModels;

    public ObservableInt loading;


    public RandomUsersFragmentsViewModel(RandomUsersRepository repository) {
        mRepository = repository;
        mUserRowModels = mRepository.getUsersList(RandomUserAnnotation.DEFAULT_UPLOAD_ARR_SIZE);
        loading = new ObservableInt(View.VISIBLE);

    }

    public LiveData<List<UserRowModel>> updateUserList() {

        mUserRowModels = mRepository.getUsersList(RandomUserAnnotation.DEFAULT_UPLOAD_ARR_SIZE);

        return mUserRowModels;
    }


    //return userListFromDb
    public LiveData<List<UserRowModel>> getOfflineUserList() {

        mUserRowModels = mRepository.getOfflineUsersList();

        return mUserRowModels;
    }
}
