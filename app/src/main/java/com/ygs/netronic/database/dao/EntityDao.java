package com.ygs.netronic.database.dao;

import com.ygs.netronic.database.entities.User;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;

public abstract class EntityDao<T> {


    public abstract Single<List<T>> select();

    public abstract Completable clear();


    @Insert
    public abstract Single<Long> insert(T item);

    @Insert
    public abstract Single<List<Long>> insert(List<T> list);

    @Update
    public abstract Single<Integer> update(T item);

    @Update
    public abstract Single<Integer> update(List<T> list);

    @Delete
    public abstract Single<Integer> delete(T item);

    @Delete
    public abstract Single<Integer> delete(List<T> list);

}
