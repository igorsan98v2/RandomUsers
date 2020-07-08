package com.ygs.netronic.activities;

import android.os.Bundle;

import com.ygs.netronic.R;
import com.ygs.netronic.annotations.GeneralString;
import com.ygs.netronic.fragments.UserDetailsFragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserDetailsActivity extends BaseActivity {
    private long mUserId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enterDataToFields(savedInstanceState);
        setContentView(R.layout.activity_random_users);
        replaceFragment(R.id.container, UserDetailsFragment.createInstance(mUserId),null);
    }



    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @Nullable String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, tag)
                .commit();
    }


    public void enterDataToFields(@NonNull Bundle args) {
            mUserId = args.getLong(GeneralString.EXTRA_USER_ID);
    }

    public void enterDataToArgs(@NonNull Bundle args) {
        args.putLong(GeneralString.EXTRA_USER_ID, mUserId);
    }


}
