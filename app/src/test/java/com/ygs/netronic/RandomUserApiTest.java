package com.ygs.netronic;

import com.google.gson.JsonElement;
import com.ygs.netronic.annotations.RandomUserAnnotation;
import com.ygs.netronic.models.network.response.UserResponseModel;
import com.ygs.netronic.services.RandomUserService;

import org.junit.Assert;
import org.junit.Test;

import retrofit2.Call;

public class RandomUserApiTest extends RandomUserTest {

    @Test
    public void getUsers() {

        Call<JsonElement> call = RandomUserService.getInstance()
                .getApi()
                .getUsers(RandomUserAnnotation.DEFAULT_UPLOAD_ARR_SIZE);

        UserResponseModel users = getUserResponse(call);
        Assert.assertNotNull(users);
    }

    @Test
    public void getWellKnownUser() {
        Call<JsonElement> call = RandomUserService.getInstance()
                .getApi()
                .getUsersByPageAndSeed(RESULTS, SEED, PAGE);
        UserResponseModel user1 = getUserResponse(call);
        call = call.clone();
        UserResponseModel user2 = getUserResponse(call);
        Assert.assertEquals(user1, user2);
    }
}
