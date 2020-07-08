package com.ygs.netronic.interfaces;

import android.view.View;

import androidx.annotation.NonNull;

public interface OnClickListener<T> {
    void onItemClick(@NonNull View view, @NonNull T model, int position);

}
