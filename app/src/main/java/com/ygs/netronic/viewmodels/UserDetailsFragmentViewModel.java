package com.ygs.netronic.viewmodels;

import android.view.ViewGroup;

import com.ygs.netronic.models.ui.UserDetailsModel;
import com.ygs.netronic.repositories.interfaces.UserDetailsRepository;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserDetailsFragmentViewModel extends ViewModel {
    private UserDetailsRepository mRepository;
    public ObservableInt loading;


    public UserDetailsFragmentViewModel(UserDetailsRepository repository) {
        mRepository = repository;
    }

    public LiveData<UserDetailsModel> getDetailsById(long userId) {
        loading.set(ViewGroup.VISIBLE);
        return mRepository.getDetailsById(userId);
    }
}
