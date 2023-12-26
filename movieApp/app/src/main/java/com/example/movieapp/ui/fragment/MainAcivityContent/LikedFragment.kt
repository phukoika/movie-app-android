package com.example.movieapp.ui.fragment.MainAcivityContent

import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentLikedBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.SwipeToDeleteCallback
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory


class LikedFragment : Fragment() {
    lateinit var binding : FragmentLikedBinding
    lateinit var movieAdapter: MovieAdapter
    lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLikedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MovieViewModel::class.java]
        movieAdapter = MovieAdapter()
        binding.movieLikedList.apply {
            this.adapter = movieAdapter
        }
        viewModel.getLocalMovie().observe(viewLifecycleOwner){
            movieAdapter.differ.submitList(it)
        }

        val swipeDeleteCallback = object : SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMovieLocal(movieAdapter.differ.currentList[position])

            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeDeleteCallback)

        itemTouchHelper.attachToRecyclerView(binding.movieLikedList)
    }


}