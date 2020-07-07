package com.ygs.netronic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ygs.netronic.converters.JsonResponseToInstanceConverter;
import com.ygs.netronic.models.network.response.UserResponseModel;

import java.io.IOException;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Response;

public abstract class RandomUserTest {
    protected final String SEED = "forpost";
    protected final int RESULTS = 1;
    protected final int PAGE = 0;

    @Nullable
    protected UserResponseModel getUserResponse(Call<JsonElement> call) {
        try {
            Response<JsonElement> response = call.execute();
            if (response.isSuccessful() && response.body() instanceof JsonObject) {
                return JsonResponseToInstanceConverter.convertToInstance((JsonObject) response.body(), UserResponseModel.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
