package com.example.movieapp.ui.fragment.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.models.NewResponse
import com.example.movieapp.models.Similar
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.LoadingDialog
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory


class MovieListFragment : Fragment() {
    lateinit var binding : FragmentMovieListBinding
    lateinit var viewModel: MovieViewModel
    lateinit var adapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val moviesViewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,moviesViewModelProviderFactory)[MovieViewModel::class.java]
        adapter = MovieAdapter(onItemClick)
        binding.movieList.apply {
            this.adapter= this@MovieListFragment.adapter
        }
        val loading = LoadingDialog(requireActivity())
        viewModel.topRated.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    it.data?.let { NewResponse ->
                        adapter.differ.submitList(NewResponse.results.toList())
                    }
//                    loading.isDismiss()
                }
                is Resource.Error -> {
                    it.message?.let {
                        Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Loading -> {
                    it.message?.let {
                        loading.startLoading()
                    }
                }

            }
        }
    }
    private val onItemClick : (com.example.movieapp.models.Result)->Unit = {
        val bundle = bundleOf(
            "result" to it
        )
        findNavController().navigate(R.id.action_categoriesFragment_to_detailFragment,bundle)
    }


}