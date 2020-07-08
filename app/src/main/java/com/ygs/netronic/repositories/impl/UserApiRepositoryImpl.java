package com.ygs.netronic.repositories.impl;

import com.google.gson.JsonElement;
import com.ygs.netronic.repositories.interfaces.UserApiRepository;
import com.ygs.netronic.services.RandomUserService;

import retrofit2.Call;

public class UserApiRepositoryImpl implements UserApiRepository {
    @Override
    public Call<JsonElement> getUsers(int results) {
        return RandomUserService.getInstance().getApi().getUsers(results);
    }

}
