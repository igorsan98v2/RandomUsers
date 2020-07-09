package com.ygs.netronic.annotations;

import androidx.annotation.StringDef;

@StringDef(ThrowableMessage.MSG_LOAD_RANDOM_USER_FRAGMENT_FIRST)
public @interface ThrowableMessage {
    String MSG_LOAD_RANDOM_USER_FRAGMENT_FIRST = "Unexpected use of UserDetailsRepository, start RandomUserFragment before UserDetailsFragment";
}
