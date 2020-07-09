package com.ygs.netronic.repositories.interfaces;

import com.ygs.netronic.models.ui.UserRowModel;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface RandomUsersRepository {
    LiveData<List<UserRowModel>> getUsersList(int usersCount);
    LiveData<List<UserRowModel>> getOfflineUsersList();
}
