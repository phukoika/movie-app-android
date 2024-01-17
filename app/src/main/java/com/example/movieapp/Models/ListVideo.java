package com.example.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListVideo {
    @SerializedName("results")
    @Expose
    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }
}
