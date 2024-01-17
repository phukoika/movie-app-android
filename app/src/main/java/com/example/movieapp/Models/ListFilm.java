package com.example.movieapp.Models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListFilm {

        @SerializedName("results")
        @Expose
        private List<Result> results;

    public List<Result> getResults() {
        return results;
    }



}
