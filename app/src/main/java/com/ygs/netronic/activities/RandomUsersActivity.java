package com.ygs.netronic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ygs.netronic.R;
import com.ygs.netronic.fragments.RandomUsersFragment;
import com.ygs.netronic.fragments.UserDetailsFragment;

public class RandomUsersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_users);
        replaceFragment(R.id.container, RandomUsersFragment.createInstance(),null);

    }

    @Override
    public void enterDataToFields(@NonNull Bundle args) {

    }

    @Override
    public void enterDataToArgs(@NonNull Bundle args) {

    }


}
