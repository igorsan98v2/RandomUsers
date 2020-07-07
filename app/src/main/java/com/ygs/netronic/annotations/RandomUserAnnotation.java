package com.ygs.netronic.annotations;

public @interface RandomUserAnnotation {
    int READ_TIMEOUT = 5;
    int CONNECT_TIMEOUT = 5;
    String API_URL = "https://randomuser.me/api/";
    int DEFAULT_UPLOAD_ARR_SIZE = 20;
}
