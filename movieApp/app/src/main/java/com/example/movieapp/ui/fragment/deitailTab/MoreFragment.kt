package com.example.movieapp.ui.fragment.deitailTab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.SimilarAdapter
import com.example.movieapp.databinding.FragmentMoreBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import java.lang.reflect.InvocationTargetException


class MoreFragment : Fragment() {
    lateinit var binding : FragmentMoreBinding
    lateinit var viewModel: MovieViewModel
    lateinit var similarAdapter: SimilarAdapter
    private var id : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            val b = arguments
            this@MoreFragment.id = b!!.getString("id")
        }
        catch (e: InvocationTargetException){
            Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_LONG).show()
        }
        val moviesRepository = MoviesRepository(MoviesDatabase(requireContext()))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),moviesRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MovieViewModel::class.java]
        viewModel.setMovieId(this@MoreFragment.id.toString(),"SIMILAR")
        similarAdapter = SimilarAdapter()
        binding.moreMovie.apply {
            this.adapter = similarAdapter
        }
        viewModel.similarMovie.observe(viewLifecycleOwner){
            when(it) {
                is Resource.Success -> {
                    it.data?.let { SimilarResponse ->
                        similarAdapter.differ.submitList(SimilarResponse.results.toList())
                    }
                }
                is Resource.Error -> {
                    it.message?.let {
                        Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                    }

                }

                is Resource.Loading->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                }
            }
        }

    }



}