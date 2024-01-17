package com.example.movieapp.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("title")
    @Expose
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500/" + posterPath;
    }



    public String getTitle() {
        return title;
    }



}
