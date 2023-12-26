package com.example.movieapp.ui.fragment.MainAcivityContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.movieapp.MoviesApplication
import com.example.movieapp.databinding.FragmentVideoBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoFragment : Fragment() {
    lateinit var binding : FragmentVideoBinding
    lateinit var viewModel: MovieViewModel

    val args : VideoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = args.id
        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MovieViewModel::class.java]
        viewModel.setMovieId(result,"VIDEO")
        viewModel.video.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    it.data?.let { VideoResponse->
                        val url = VideoResponse.results[0].key
                        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo(url, 0F)
                            }
                        })
                    }
                }
                is Resource.Error -> {
                    it.message?.let {
                        Toast.makeText(requireContext(),"Error", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                }


            }
        }

    }

}