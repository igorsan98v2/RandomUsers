package com.ygs.netronic.converters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JsonResponseToInstanceConverter {
    @Nullable
    public static <T> T convertToInstance(@NonNull JsonObject jsonObject, @NonNull Class<T> clazz) {
        Gson gson = new Gson();
        T instance = null;
        try {
            instance = gson.fromJson(jsonObject, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return instance;
    }


}
