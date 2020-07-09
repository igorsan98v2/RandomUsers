package com.ygs.netronic.database.dao;

import com.ygs.netronic.database.entities.User;
import com.ygs.netronic.database.samples.UserDetailsSample;
import com.ygs.netronic.database.samples.UserRowSample;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public abstract class UserDao extends EntityDao<User> {
    @Query("SELECT * FROM User")
    @Override
    public abstract Single<List<User>> select();


    @Query("SELECT * FROM User WHERE id =:userId")
    public abstract Single<User> selectById(long userId);

    @Query("SELECT " +
            "User.id as id, " +
            "User.firstName as firstName, " +
            "User.lastName as lastName, " +
            "PictureUrl.thumbnail as thumbnailUrl " +
            "FROM User " +
            "LEFT JOIN PictureUrl " +
            "ON User.id = PictureUrl.userId")
    public abstract Single<List<UserRowSample>> selectRowSamples();

    @Query("SELECT " +
            "User.firstName as firstName, " +
            "User.lastName as lastName, " +
            "User.title as title, " +
            "User.gender as gender, " +
            "User.age as age, " +
            "User.email as email, " +
            "PictureUrl.large as picture, " +
            "Location.country as country, " +
            "Location.city as city, " +
            "Location.streetName as streetName, " +
            "Location.streetNumber as streetNumber " +
            "FROM User " +
            "LEFT JOIN PictureUrl " +
            "ON User.id = PictureUrl.userId " +
            "LEFT JOIN Location " +
            "ON User.id = Location.userId " +
            "WHERE User.id = :userId")
    public abstract Single<UserDetailsSample> selectDetailsSampleByUserId(long userId);

    @Query("DELETE FROM User")
    @Override
    public abstract Completable clear();

}
