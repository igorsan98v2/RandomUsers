package com.ygs.netronic.activities;

import android.content.Intent;
import android.os.Bundle;

import com.ygs.netronic.interfaces.Restorable;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class BaseActivity extends AppCompatActivity implements Restorable {


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        enterDataToArgs(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @Nullable String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, tag)
                .commit();
    }



    public void restoreState(Bundle state) {
        if (state != null) {
            enterDataToFields(state);
        } else {
            Intent intent = getIntent();
            Bundle args = intent.getExtras();
            enterDataToFields(args);
        }
    }

    public abstract void enterDataToFields(@NonNull Bundle args);

    public abstract void enterDataToArgs(@NonNull Bundle args);
}
