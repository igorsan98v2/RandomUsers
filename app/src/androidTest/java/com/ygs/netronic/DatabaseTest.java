package com.ygs.netronic;

import android.content.Context;

import com.google.gson.JsonElement;
import com.ygs.netronic.database.AppDatabase;
import com.ygs.netronic.database.DatabaseManager;
import com.ygs.netronic.database.RandomUserDatabase;
import com.ygs.netronic.database.entities.Location;
import com.ygs.netronic.database.entities.PictureUrl;
import com.ygs.netronic.database.entities.User;
import com.ygs.netronic.models.network.response.UserResponseModel;
import com.ygs.netronic.services.RandomUserService;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import androidx.room.EmptyResultSetException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import retrofit2.Call;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class DatabaseTest extends RandomUserTestInstrumental {

    private User user;
    private Location location;
    private PictureUrl pictureUrl;
    private long locationId;
    private long userId;
    private long pictureId;

    private AppDatabase getDatabase() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase database = RandomUserDatabase.getInstance(context);
        DatabaseManager.getInstance().setCurrentDatabase(database);
        return DatabaseManager.getInstance().getCurrentDatabase();
    }


    @Test
    public void stage0_userWrite() {
        Call<JsonElement> call = RandomUserService.getInstance()
                .getApi()
                .getUsersByPageAndSeed(RESULTS, SEED, PAGE);
        UserResponseModel response = getUserResponse(call);
        if (response != null && response.userDataList.size() > 0) {
            UserResponseModel.UserData userData = response.userDataList.get(0);
            user = (User) userData.mapToLocal();
            pictureUrl = (PictureUrl) userData.picture.mapToLocal();
            location = (Location) userData.location.mapToLocal();
            AppDatabase database = getDatabase();

            database.runInTransaction(()->{
              userId =  database.userDao().insert(user).blockingGet();

              location.userId = userId;
              pictureUrl.userId = userId;

              pictureId = database.pictureUrlDao().insert(pictureUrl).blockingGet();
              locationId = database.locationDao().insert(location).blockingGet();
            });


        } else {
            Assert.fail();
        }
    }

    @Test
    public void stage1_userRead() {
        AppDatabase database = getDatabase();

        User rUser = database.userDao().selectById(userId).blockingGet();
        Location rLocation = database.locationDao().selectById(locationId).blockingGet();
        PictureUrl rPictureUrl = database.pictureUrlDao().selectById(pictureId).blockingGet();


        Assert.assertEquals(user,rUser);
        Assert.assertEquals(location,rLocation);
        Assert.assertEquals(pictureUrl, rPictureUrl);
    }


    @Test
    public void stage2_userDelete() {
        AppDatabase database = getDatabase();
        user.id = userId;
        database.userDao().delete(user).blockingGet();

        try {
            database.pictureUrlDao().selectById(pictureId).blockingGet();
            database.locationDao().selectById(locationId).blockingGet();
            Assert.fail();
        }
        catch (EmptyResultSetException e){
            e.printStackTrace();

        }

    }


}
