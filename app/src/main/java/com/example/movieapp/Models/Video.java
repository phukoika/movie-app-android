package com.example.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("key")
    @Expose
    private String key;

    public String getKey() {
        return key;
    }
}
