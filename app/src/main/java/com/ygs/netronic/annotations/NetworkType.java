package com.ygs.netronic.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@Retention(RetentionPolicy.RUNTIME)
@IntDef(value = {
        NetworkType.CELLULAR,
        NetworkType.WIFI
})
public @interface NetworkType {

    int CELLULAR = 0;
    int WIFI = 1;
}
