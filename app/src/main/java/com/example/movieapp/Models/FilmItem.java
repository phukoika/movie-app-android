
package com.example.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmItem {
    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("genres")
    @Expose
    private List<Genres> genres;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500/" + posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {return overview;}
    public List<Genres> getGenres() {
        return genres;
    }
}
