package com.example.xiongapp.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;
    public String lat;
    public String lon;
    public class Update {

        @SerializedName("loc")
        public String updateTime;

    }

}
