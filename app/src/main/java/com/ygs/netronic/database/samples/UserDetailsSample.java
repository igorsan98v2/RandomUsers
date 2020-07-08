package com.ygs.netronic.database.samples;

import com.ygs.netronic.interfaces.mappers.UserDetailsSampleMapper;
import com.ygs.netronic.models.ui.UserDetailsModel;

public class UserDetailsSample implements UserDetailsSampleMapper {
    public String firstName;
    public String lastName;
    public String picture;
    public String gender;
    public String title;
    public int age;
    public String country;
    public String city;
    public String streetName;
    public String streetNumber;
    public String email;

    @Override
    public UserDetailsModel mapToUserDetailsModel() {
        return new UserDetailsModel.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setTitle(title)
                .setGender(gender)
                .setAge(age)
                .setEmail(email)
                .setCountry(country)
                .setStreet(streetName + " " + streetNumber)
                .setPicture(picture)
                .create();
    }
}
