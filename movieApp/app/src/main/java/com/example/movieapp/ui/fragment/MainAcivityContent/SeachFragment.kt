package com.example.movieapp.ui.fragment.MainAcivityContent

import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.adapter.SimilarAdapter
import com.example.movieapp.databinding.FragmentSeachBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.models.NewResponse
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SeachFragment : Fragment() {
    lateinit var binding : FragmentSeachBinding
    lateinit var adapter : MovieAdapter
    lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentSeachBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val moviesViewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,moviesViewModelProviderFactory)[MovieViewModel::class.java]
        adapter = MovieAdapter(onClicItem)
        binding.rvSearch.apply {
            this.adapter = this@SeachFragment.adapter
        }
        viewModel.popularMovies.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    it.data?.let {  NewResponse->
                        Toast.makeText(requireContext(),NewResponse.results.size.toString(),Toast.LENGTH_LONG).show()
                        adapter.differ.submitList(NewResponse.results.toList())
                    }
                }
                is Resource.Error -> {
                    it.message?.let{
                        Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                }

            }
        }
            var job: Job? = null
            binding.searchEdit.addTextChangedListener {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    it?.let {
                        if(it.toString().isNotEmpty()){
                            viewModel.searchMovie(it.toString())
                        }
                    }
                }
            }

    }
    val onClicItem : (com.example.movieapp.models.Result)->Unit = {
        val bundle = bundleOf(
            "result" to it
        )
        findNavController().navigate(R.id.action_seachFragment_to_detailFragment,bundle)
    }


}