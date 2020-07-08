package com.ygs.netronic.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseManager {
    private static volatile DatabaseManager mInstance;
    private volatile AppDatabase currentDatabase;


    private DatabaseManager() {
    }

    @Nullable
    public synchronized AppDatabase getCurrentDatabase() {
        return currentDatabase;
    }

    public synchronized void setCurrentDatabase(@Nullable final AppDatabase currentDatabase) {
        this.currentDatabase = currentDatabase;
    }



    @NonNull
    public static DatabaseManager getInstance() {
        DatabaseManager localInstance = mInstance;
        if (localInstance == null) {
            synchronized (DatabaseManager.class) {
                localInstance = mInstance;
                if (localInstance == null) {
                    mInstance = localInstance = new DatabaseManager();
                }
            }
        }
        return localInstance;
    }


}
