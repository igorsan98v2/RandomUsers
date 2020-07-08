package com.ygs.netronic.database.dao;

import com.ygs.netronic.database.entities.Location;
import com.ygs.netronic.database.entities.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;
@Dao
public abstract class LocationDao extends EntityDao<Location> {
    @Query("SELECT * FROM Location")
    @Override
    public abstract Single<List<Location>> select();

    @Query("SELECT * FROM Location WHERE id =:locationId")
    public abstract Single<Location> selectById(long locationId);


    @Query("DELETE FROM Location")
    @Override
    public abstract Completable clear();

}
