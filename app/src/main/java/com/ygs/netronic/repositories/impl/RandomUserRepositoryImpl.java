package com.ygs.netronic.repositories.impl;

import com.google.gson.JsonElement;
import com.ygs.netronic.api.RandomUserApi;
import com.ygs.netronic.repositories.interfaces.RandomUserRepository;
import com.ygs.netronic.services.RandomUserService;

import retrofit2.Call;

public class RandomUserRepositoryImpl implements RandomUserRepository {
    @Override
    public Call<JsonElement> getUsers(int results) {
        return RandomUserService.getInstance().getApi().getUsers(results);
    }

}
