package com.ygs.netronic.api;


import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApi {

    /**return random collection of users*/
    @GET(".")
    Call<JsonElement> getUsers(@Query("results") int results);

    /**return predictable collection of users or error*/
    @GET(".")
    Call<JsonElement> getUsersByPageAndSeed(@Query("results") int results,
                                            @Query("seed") String seed,
                                            @Query("page") int page);

}
