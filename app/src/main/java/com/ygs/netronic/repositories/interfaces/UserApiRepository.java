package com.ygs.netronic.repositories.interfaces;

import com.google.gson.JsonElement;

import retrofit2.Call;

public interface UserApiRepository {
    Call<JsonElement> getUsers(int results);
}
