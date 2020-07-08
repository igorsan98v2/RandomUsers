package com.ygs.netronic.database.dao;

import com.ygs.netronic.database.entities.PictureUrl;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;
@Dao
public abstract class PictureUrlDao extends EntityDao<PictureUrl> {
    @Query("SELECT * FROM PictureUrl")
    @Override
    public abstract Single<List<PictureUrl>> select();

    @Query("SELECT * FROM PictureUrl WHERE id =:pictureId")
    public abstract Single<PictureUrl> selectById(long pictureId);


    @Query("DELETE FROM PictureUrl")
    @Override
    public abstract Completable clear();

}
