package com.example.movieapp.ui.fragment.deitailTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.ReviewAdapter
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.databinding.FragmentTrailerBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.models.ReviewsResponse
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import java.lang.reflect.InvocationTargetException


class TrailerFragment : Fragment() {
    lateinit var binding: FragmentTrailerBinding
    lateinit var reviewAdapter: ReviewAdapter
    lateinit var viewModel: MovieViewModel
    private var id : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTrailerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val b = arguments
            this@TrailerFragment.id = b!!.getString("id")
            Toast.makeText(requireContext(),this@TrailerFragment.id,Toast.LENGTH_LONG).show()
        }
        catch (e: InvocationTargetException){
            Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_LONG).show()
        }
        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val moviesViewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,moviesViewModelProviderFactory)[MovieViewModel::class.java]
        viewModel.setMovieId(this@TrailerFragment.id.toString(),"REVIEW")
        reviewAdapter = ReviewAdapter()
        binding.rvReviews.apply {
            this.adapter = reviewAdapter
        }

        viewModel.reviews.observe(viewLifecycleOwner){
            when(it) {
                is Resource.Success -> {
                    it.data?.let {  ReviewsResponse ->
                        reviewAdapter.differ.submitList(ReviewsResponse.results.toList())
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),"Error review",Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}