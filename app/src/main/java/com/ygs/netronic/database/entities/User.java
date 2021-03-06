package com.ygs.netronic.database.entities;

import com.ygs.netronic.interfaces.Local;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Local {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String firstName;
    public String lastName;
    public String title;
    public String gender;
    public String email;
    public int age;


    @Override
    public int hashCode() {
        return firstName.hashCode()
                + lastName.hashCode()
                + gender.hashCode()
                + Integer.hashCode(age);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof User) {
            return hashCode() == obj.hashCode();
        }
        return false;
    }
}
