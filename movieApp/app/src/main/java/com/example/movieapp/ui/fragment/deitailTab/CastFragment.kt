package com.example.movieapp.ui.fragment.deitailTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.MoviesApplication
import com.example.movieapp.adapter.ActorsAdapter
import com.example.movieapp.databinding.FragmentCastBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.models.CastResponse
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import java.lang.reflect.InvocationTargetException


class CastFragment : Fragment() {
    lateinit var binding : FragmentCastBinding
    lateinit var actorsAdapter: ActorsAdapter
    lateinit var viewModel: MovieViewModel
    lateinit var result: com.example.movieapp.models.Result
    private var id : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val b = arguments
            this@CastFragment.id = b!!.getString("id")

        }
        catch (e: InvocationTargetException){
            Toast.makeText(requireContext(),e.message.toString(),Toast.LENGTH_LONG).show()
        }

        val moviesRepository = MoviesRepository(MoviesDatabase(requireContext()))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),moviesRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MovieViewModel::class.java]
        viewModel.setMovieId(this@CastFragment.id.toString(),"ACTOR")
        actorsAdapter = ActorsAdapter()
        binding.rvCast.apply {
            this.adapter = actorsAdapter
        }
        viewModel.actors.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                   it.data?.let { CastResponse->
                       actorsAdapter.differ.submitList(CastResponse.cast.toList())
                   }
                }
                is Resource.Error -> {
                    it.message?.let {
                        Toast.makeText(requireContext(),"error",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                }


            }
        }

    }



}