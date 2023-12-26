package com.example.movieapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager2 viewPager2;
    private RecyclerView recyclerViewNowPlaying, recyclerViewTrending,recyclerViewUpcoming;
    private RecyclerView.Adapter adapterBestMovies, adapterTrending, adapterUpcoming;
    private StringRequest mStringRequest,mStringRequest2, mStringRequest3;
    private RequestQueue mRequestQueue;
    private ProgressBar loading1, loading2, loading3;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void sendRequestTopRate() {
        mRequestQueue = Volley.newRequestQueue(requireContext());
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
        mRequestQueue = Volley.newRequestQueue(requireContext());
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

    private void sendRequestNowPlaying() {
        mRequestQueue = Volley.newRequestQueue(requireContext());
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


    private void initView(View view) {
        viewPager2 = view.findViewById(R.id.viewPagerSlider);
        recyclerViewNowPlaying = view.findViewById(R.id.view1);
        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrending = view.findViewById(R.id.view3);
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcoming = view.findViewById(R.id.view2);
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        loading1 = view.findViewById(R.id.progressBar);
        loading2 = view.findViewById(R.id.progressBar2);
        loading3 = view.findViewById(R.id.progressBar3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        banner();
        sendRequestNowPlaying();
        sendRequestUpcoming();
        sendRequestTopRate();

        return view;
    }
}