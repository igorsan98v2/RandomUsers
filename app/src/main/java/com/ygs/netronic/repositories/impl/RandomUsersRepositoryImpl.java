package com.ygs.netronic.repositories.impl;

import com.ygs.netronic.models.ui.UserRowModel;
import com.ygs.netronic.repositories.interfaces.RandomUsersRepository;
import com.ygs.netronic.repositories.interfaces.UserApiRepository;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RandomUsersRepositoryImpl implements RandomUsersRepository {

    private UserApiRepository userApiRepository = new UserApiRepositoryImpl();

    @Override
    public LiveData<List<UserRowModel>> getUsersList(int usersCount) {
        return null;
    }


}
