package com.ygs.netronic.models.ui;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ygs.netronic.R;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

public class UserRowModel extends BaseObservable {
    private long userId;
    private String thumbnailUrl;
    private String firstName;
    private String lastName;

    private UserRowModel() {
    }

    @Bindable
    public long getUserId() {
        return userId;
    }

    @Bindable
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    @BindingAdapter("android:src")
    public static void imageLoader(ImageView imageView, String url) {
        Picasso.get().load(url).placeholder(R.drawable.unloaded_image).fit().into(imageView);
    }

    public static class Builder {
        private long userId;
        private String thumbnailUrl;
        private String firstName;
        private String lastName;

        public Builder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
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

        public UserRowModel create() {
            UserRowModel instance = new UserRowModel();
            instance.firstName = firstName;
            instance.lastName = lastName;
            instance.thumbnailUrl = thumbnailUrl;
            instance.userId = userId;
            return instance;
        }


    }

}
