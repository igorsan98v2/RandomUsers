package com.ygs.netronic.interfaces;

import android.os.Bundle;

import androidx.annotation.NonNull;

public interface Restorable {
    void enterDataToFields(@NonNull Bundle args);
    void enterDataToArgs(@NonNull Bundle args);
}
