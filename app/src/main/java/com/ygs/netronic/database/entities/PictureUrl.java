package com.ygs.netronic.database.entities;

import com.ygs.netronic.interfaces.Local;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        indices = {
                @Index("userId")
        },
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                )
        }
)
public class PictureUrl implements Local {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String large;
    public String medium;
    public String thumbnail;

    public long userId;


    @Override
    public int hashCode() {
        return thumbnail.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PictureUrl) {
            return hashCode() == obj.hashCode();
        }
        return false;
    }
}
