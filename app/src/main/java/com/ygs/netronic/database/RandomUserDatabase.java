package com.ygs.netronic.database;

import android.content.Context;

import com.ygs.netronic.annotations.RandomUserAnnotation;
import com.ygs.netronic.database.entities.Location;
import com.ygs.netronic.database.entities.PictureUrl;
import com.ygs.netronic.database.entities.User;

import androidx.annotation.NonNull;
import androidx.room.Room;
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory;

@androidx.room.Database(
        version = AppDatabase.VERSION,
        exportSchema = false,
        entities = {
                User.class,
                PictureUrl.class,
                Location.class
        }
)
public abstract class RandomUserDatabase extends AppDatabase {
    private static volatile RandomUserDatabase instance;
    
    @NonNull
    public static RandomUserDatabase getInstance(Context context) {
        RandomUserDatabase localInstance = instance;
        if (localInstance == null) {
            synchronized (RandomUserDatabase.class) {
                localInstance = instance;
                if (localInstance == null) {
                    String name = RandomUserAnnotation.DATABASE_NANE;
                    instance = localInstance =
                            Room.databaseBuilder(context, RandomUserDatabase.class, name)
                                    .fallbackToDestructiveMigration()
                                    .openHelperFactory(new RequerySQLiteOpenHelperFactory())
                                    .build();
                    instance.addToLowerCaseFunction(instance.getOpenHelper().getWritableDatabase());
                }
            }
        }
        return localInstance;
    }


}
