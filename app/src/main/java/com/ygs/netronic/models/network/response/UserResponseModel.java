package com.ygs.netronic.models.network.response;

import com.google.gson.annotations.SerializedName;

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

    class UserData {
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
            if(obj instanceof UserData){
                return hashCode() == obj.hashCode();
            }
            return false;
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

    public class Location {

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

    public class Picture {
        @SerializedName("large")
        public String large;
        @SerializedName("medium")
        public String medium;
        @SerializedName("thumbnail")
        public String thumbnail;

    }


}
