package com.example.movieapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapters.FilmListAdapter;
import com.example.movieapp.Models.ListFilm;
import com.example.movieapp.Models.Result;
import com.example.movieapp.R;
import com.example.movieapp.ultis.api;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private EditText mEditTextSearch;
    private RecyclerView mRecyclerViewSearch;
    private RecyclerView.Adapter mSearchAdapter;
    private ProgressBar mProgressBarLoading;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private Button btnSearch;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mEditTextSearch.getText().toString().trim();
                Log.v("TAG", "Hi" + query);
                sendRequestSearch(query);
            }
        });
        return view;
    }



    private void sendRequestSearch(String query) {
        mRequestQueue = Volley.newRequestQueue(requireContext());
        mProgressBarLoading.setVisibility(View.VISIBLE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/search/movie?api_key=" + api.API_KEY + "&query=" + query,
                response -> {
                    Gson gson = new Gson();
                    mProgressBarLoading.setVisibility(View.GONE);
                    ListFilm items = gson.fromJson(response, ListFilm.class);
                    mSearchAdapter = new FilmListAdapter(items);
                    mRecyclerViewSearch.setAdapter(mSearchAdapter);
                },
                error -> {
                    mProgressBarLoading.setVisibility(View.GONE);
                    Log.i("TAG:", "Error:" + error.toString());
                });

        mRequestQueue.add(mStringRequest);
    }

    private void initView(View view) {
        mRecyclerViewSearch = view.findViewById(R.id.searchView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        mRecyclerViewSearch.setLayoutManager(gridLayoutManager);
        mProgressBarLoading = view.findViewById(R.id.loading_search);
        mEditTextSearch = view.findViewById(R.id.editTextSearch);
        btnSearch = view.findViewById(R.id.btnSearch);

    }
}