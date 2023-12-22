package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapters.FilmListAdapter;
import com.example.movieapp.Adapters.SliderAdapter;
import com.example.movieapp.Models.ListFilm;
import com.example.movieapp.Models.SliderItem;
import com.example.movieapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private FirebaseAuth fAuth;
    private ViewPager2 viewPager2;
    private RecyclerView recyclerViewNowPlaying, recyclerViewTrending,recyclerViewUpcoming;
    private RecyclerView.Adapter adapterBestMovies, adapterTrending, adapterUpcoming;
    private StringRequest mStringRequest,mStringRequest2, mStringRequest3;
    private RequestQueue mRequestQueue;
    private ProgressBar loading1, loading2, loading3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        banner();
        sendRequestNowPlaying();
        sendRequestUpcoming();
        sendRequestTopRate();
    }

    private void sendRequestNowPlaying() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/now_playing?api_key=fc2329e047ad9dc86a7e9f65dfd8b9e5",
                response -> {
                    Gson gson = new Gson();
                    loading1.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    adapterBestMovies = new FilmListAdapter(items);
                    recyclerViewNowPlaying.setAdapter(adapterBestMovies);
                },
                error -> {
                    loading1.setVisibility(View.GONE);
                    Log.i("TAG:", "Error:" + error.toString());
                });

        mRequestQueue.add(mStringRequest);
    }
    private void sendRequestTopRate() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading3.setVisibility(View.VISIBLE);

        mStringRequest3 = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/top_rated?api_key=fc2329e047ad9dc86a7e9f65dfd8b9e5",
                response -> {
                    Gson gson = new Gson();
                    loading3.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    adapterTrending = new FilmListAdapter(items);
                    recyclerViewTrending.setAdapter(adapterTrending);
                },
                error -> {
                    loading3.setVisibility(View.GONE);
                    Log.i("TAG:", "Error:" + error.toString());
                });

        mRequestQueue.add(mStringRequest3);
    }
    private void sendRequestUpcoming() {
        mRequestQueue = Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);

        mStringRequest2 = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/upcoming?api_key=fc2329e047ad9dc86a7e9f65dfd8b9e5",
                response -> {
                    Gson gson = new Gson();
                    loading2.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    adapterUpcoming = new FilmListAdapter(items);
                    recyclerViewUpcoming.setAdapter(adapterUpcoming);
                },
                error -> {
                    loading2.setVisibility(View.GONE);
                    Log.i("TAG:", "Error:" + error.toString());
                });

        mRequestQueue.add(mStringRequest2);
    }



    private void banner() {
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.wide));
        sliderItems.add(new SliderItem(R.drawable.wide1));
        sliderItems.add(new SliderItem(R.drawable.wide2));
        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r*0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewPagerSlider);
        recyclerViewNowPlaying = findViewById(R.id.view1);
        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrending = findViewById(R.id.view3);
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcoming = findViewById(R.id.view2);
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading1 = findViewById(R.id.progressBar);
        loading2 = findViewById(R.id.progressBar2);
        loading3 = findViewById(R.id.progressBar3);
    }


}