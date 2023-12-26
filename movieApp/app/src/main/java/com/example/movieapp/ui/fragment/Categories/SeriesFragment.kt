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
import com.example.movieapp.adapter.SeriesAdapter
import com.example.movieapp.databinding.FragmentMovieListBinding
import com.example.movieapp.databinding.FragmentSeriesBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.models.Series
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.LoadingDialog
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory


class SeriesFragment : Fragment() {
    lateinit var binding : FragmentSeriesBinding
    lateinit var viewModel: MovieViewModel
    lateinit var adapter: SeriesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val moviesViewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,moviesViewModelProviderFactory)[MovieViewModel::class.java]
        adapter = SeriesAdapter(onClickItem)
        binding.seriesList.apply {
            this.adapter= this@SeriesFragment.adapter
        }
        val loading = LoadingDialog(requireActivity())
        viewModel.seriesTv.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    it.data?.let { SeriesResponse ->
                        Toast.makeText(requireContext(),SeriesResponse.results.size.toString(), Toast.LENGTH_LONG).show()
                        adapter.differ.submitList(SeriesResponse.results.toList())
                    }

                }
                is Resource.Error -> {
                    it.message?.let {
                        Toast.makeText(requireContext(),"Error", Toast.LENGTH_LONG).show()
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
    val onClickItem : (Series)->Unit = {
        val bundle = bundleOf(
            "result" to it
        )
        findNavController().navigate(R.id.action_categoriesFragment_to_detailFragment,bundle)
    }

}