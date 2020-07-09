package com.ygs.netronic.models.network.response;

import com.google.gson.annotations.SerializedName;
import com.ygs.netronic.database.entities.PictureUrl;
import com.ygs.netronic.database.entities.User;
import com.ygs.netronic.interfaces.Foreign;
import com.ygs.netronic.interfaces.Local;
import com.ygs.netronic.interfaces.Remote;

import java.util.List;

import androidx.annotation.Nullable;

public class UserResponseModel {

    @SerializedName("results")
    public List<UserData> userDataList;


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof UserResponseModel) {
            UserResponseModel responseModel = (UserResponseModel) obj;
            return userDataList.equals(responseModel.userDataList);
        }
        return false;
    }

    public class UserData implements Remote {
        @SerializedName("gender")
        public String gender;

        @SerializedName("email")
        public String email;

        @SerializedName("name")
        public Name name;

        @SerializedName("dob")
        public DateOfBirt dob;

        @SerializedName("location")
        public Location location;

        @SerializedName("picture")
        public Picture picture;

        @Override
        public int hashCode() {
            return email.hashCode()
                    + name.first.hashCode()
                    + name.last.hashCode()
                    + dob.date.hashCode()
                    + location.street.name.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof UserData) {
                return hashCode() == obj.hashCode();
            }
            return false;
        }

        @Override
        public Local mapToLocal() {
            User instance = new User();
            instance.age = dob.age;
            instance.firstName = name.first;
            instance.lastName = name.last;
            instance.title = name.title;
            instance.gender = gender;
            instance.email = email;
            return instance;
        }
    }

    public class Name {

        @SerializedName("title")
        public String title;

        @SerializedName("first")
        public String first;

        @SerializedName("last")
        public String last;

    }

    public class DateOfBirt {

        @SerializedName("date")
        public String date;

        @SerializedName("age")
        public int age;
    }

    public class Location implements Remote, Foreign {

        @SerializedName("street")
        public Street street;
        @SerializedName("city")
        public String city;
        @SerializedName("state")
        public String state;
        @SerializedName("country")
        public String country;
        @SerializedName("postcode")
        public String postcode;
        @SerializedName("coordinates")
        public Coordinates coordinates;
        @SerializedName("timezone")
        public Timezone timezone;

        @Override
        public Local mapToLocal() {
            com.ygs.netronic.database.entities.Location instance = new com.ygs.netronic.database.entities.Location();
            instance.city = city;
            instance.country = country;
            instance.state = state;
            instance.streetName = street.name;
            instance.streetNumber = street.number;
            return instance;
        }


        @Override
        public void appendToLocalList(long foreignKeyId, List list) {
            com.ygs.netronic.database.entities.Location instance
                    = (com.ygs.netronic.database.entities.Location) mapToLocal();
            instance.userId = foreignKeyId;
            list.add(instance);
        }
    }

    public class Street {
        public String number;
        public String name;
    }


    public class Coordinates {
        @SerializedName("latitude")
        public double latitude;
        @SerializedName("longitude")
        public double longitude;
    }

    public class Timezone {

        @SerializedName("offset")
        public String offset;

        @SerializedName("description")
        public String description;

    }

    public class Picture implements Remote, Foreign {
        @SerializedName("large")
        public String large;
        @SerializedName("medium")
        public String medium;
        @SerializedName("thumbnail")
        public String thumbnail;

        @Override
        public Local mapToLocal() {
            PictureUrl instance = new PictureUrl();
            instance.large = large;
            instance.medium = medium;
            instance.thumbnail = thumbnail;
            return instance;
        }


        @Override
        public void appendToLocalList(long foreignKeyId, List list) {
            PictureUrl pictureUrl = (PictureUrl) mapToLocal();
            pictureUrl.userId = foreignKeyId;
            list.add(pictureUrl);

        }
    }


}
