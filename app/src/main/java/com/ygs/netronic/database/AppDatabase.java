package com.ygs.netronic.database;


import com.ygs.netronic.database.dao.LocationDao;
import com.ygs.netronic.database.dao.PictureUrlDao;
import com.ygs.netronic.database.dao.UserDao;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import io.requery.android.database.sqlite.SQLiteDatabase;

public abstract class AppDatabase extends RoomDatabase {

    private final SQLiteDatabase.Function toLowerCaseFunction = (args, result) ->
            result.set(args.getString(0).toLowerCase());

    public static final int VERSION = 1;

    public abstract UserDao userDao();

    public abstract PictureUrlDao pictureUrlDao();

    public abstract LocationDao locationDao();

    protected void addToLowerCaseFunction(@NonNull SupportSQLiteDatabase database) {
        ((SQLiteDatabase) database).addFunction("to_lower_case", 1, toLowerCaseFunction);
    }

}
