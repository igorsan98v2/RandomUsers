package com.ygs.netronic.models.ui;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ygs.netronic.R;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

public class UserDetailsModel extends BaseObservable {
    private String firstName;
    private String lastName;
    private String title;
    private int age;
    private String country;
    private String city;
    private String picture;
    private String gender;
    private String street;
    private String email;

    @Bindable
    @NonNull
    public String getFullName() {
        return title
                + " "
                + lastName
                + " "
                + firstName;
    }

    @Bindable
    @NonNull
    public String getPictureUrl() {
        return picture;
    }

    @Bindable
    @NonNull
    public String getGender() {
        return gender;
    }

    @Bindable
    @NonNull
    public int getAge() {
        return age;
    }

    @Bindable
    @NonNull
    public String getCountry() {
        return country;
    }

    @Bindable
    @NonNull
    public String getCity() {
        return city;
    }

    @Bindable
    @NonNull
    public String getStreet() {
        return street;
    }

    @Bindable
    @NonNull
    public String getEmail() {
        return email;
    }

    @BindingAdapter("android:imgSrc")
    public static void imageLoader(ImageView imageView, String url) {
        Picasso.get().load(url).placeholder(R.drawable.unloaded_image).resizeDimen(R.dimen.dp_270, R.dimen.dp_270).centerInside().into(imageView);
    }

    public static class Builder {
        private String email;
        private int age;
        private String gender;
        private String firstName;
        private String lastName;
        private String title;
        private String country;
        private String city;
        private String picture;
        private String street;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public UserDetailsModel create(){
            UserDetailsModel instance = new UserDetailsModel();
            instance.picture = picture;
            instance.firstName = firstName;
            instance.lastName = lastName;
            instance.title = title;
            instance.age = age;
            instance.gender = gender;
            instance.country = country;
            instance.city = city;
            instance.street = street;
            instance.email = email;
            return instance;
        }
    }

}
