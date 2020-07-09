package com.ygs.netronic.repositories.interfaces;

import com.ygs.netronic.models.ui.UserDetailsModel;

import androidx.lifecycle.LiveData;

public interface UserDetailsRepository {
    LiveData<UserDetailsModel> getDetailsById(long userId);
}
