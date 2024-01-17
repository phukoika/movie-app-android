package com.example.movieapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movieapp.Adapters.CategoryAdapter;
import com.example.movieapp.Adapters.FilmListAdapter;
import com.example.movieapp.Models.FilmItem;
import com.example.movieapp.Models.ListFilm;
import com.example.movieapp.R;
import com.example.movieapp.ultis.api;
import com.google.gson.Gson;

public class DetailFilmActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar progressBar;
    private TextView tvTitle, tvRate, tvRelease, tvOverView;
    private int idFilm;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterSimilar;
    private RecyclerView.Adapter adapterCategory;
    private RecyclerView recyclerViewSimilar, recyclerViewCategory;
    private NestedScrollView scrollView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        idFilm = getIntent().getIntExtra("id", 0);
        Log.v("Tag", "Id : " + idFilm);
        initView();
        sendRequest();
        sendRequestSimilar();
    }

    private void sendRequestSimilar() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);


        mStringRequest2 = new StringRequest(Request.Method.GET, api.BASE_URL + idFilm +"/similar?api_key=" + api.API_KEY,
                response -> {
                    Gson gson = new Gson();
                    progressBar.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    adapterSimilar = new FilmListAdapter(items);
                    recyclerViewSimilar.setAdapter(adapterSimilar);
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Log.i("TAG:", "Error:" + error.toString());
                });

        mRequestQueue.add(mStringRequest2);
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        mStringRequest = new StringRequest(Request.Method.GET, api.BASE_URL + idFilm + "?api_key=" + api.API_KEY, response -> {
            Gson gson = new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            FilmItem item = gson.fromJson(response, FilmItem.class);
            Glide.with(DetailFilmActivity.this)
                    .load(item.getPosterPath())
                    .into(pic2);
            tvTitle.setText(item.getOriginalTitle());
            tvRate.setText(String.valueOf(item.getVoteAverage()));
            tvRelease.setText(item.getReleaseDate());
            tvOverView.setText(item.getOverview());
            if(item.getGenres() != null) {
                adapterCategory = new CategoryAdapter(item.getGenres());
                recyclerViewCategory.setAdapter(adapterCategory);
            }

        }, error -> Log.i("TAG:", "Error:" + error.toString()));
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvMovieName);
        progressBar = findViewById(R.id.loadingSimilar);
        backImg = findViewById(R.id.back);
        pic2 = findViewById(R.id.imgDetail);
        tvRate = findViewById(R.id.tvMovieStar);
        tvRelease = findViewById(R.id.tvMovieTime);
        tvOverView = findViewById(R.id.tvOverView);
        scrollView = findViewById(R.id.scrollView2);
        recyclerViewSimilar = findViewById(R.id.viewSimilar);
        recyclerViewSimilar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory = findViewById(R.id.categoryView);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
