package com.example.movieapp.ui.fragment.MainAcivityContent

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.DetailAdapter
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.models.Series
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.ui.fragment.deitailTab.CastFragment
import com.example.movieapp.utils.LoadingDialog
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DetailFragment : Fragment() {
    lateinit var binding : FragmentDetailBinding
    lateinit var detailAdapter: DetailAdapter
    lateinit var viewModel: MovieViewModel
    val args : DetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = args.result
        detailAdapter = DetailAdapter(parentFragmentManager,lifecycle,result)

        binding.tabLayout.addTab(tabLayout.newTab().setText("Trailer"))
        binding.tabLayout.addTab(tabLayout.newTab().setText("Cast"))
        binding.tabLayout.addTab(tabLayout.newTab().setText("More"))
        binding.viewPager.adapter = detailAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
            tab.text = when(position){
                0-> "Trailer"
                1-> "Cast"
                2-> "More"
                else -> Resources.NotFoundException("Not found").toString()
            }

        }.attach()
        setUp(result)

        val repository = MoviesRepository(MoviesDatabase(requireContext()))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MovieViewModel::class.java]
        binding.saveMovieBtn.setOnClickListener{
            viewModel.insertMovieToLocal(result)
        }
        binding.btnTrailer.setOnClickListener{
            val bundle = bundleOf(
                "id" to result.id.toString()
            )
            Toast.makeText(requireContext(),result.id.toString(),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_detailFragment_to_videoFragment,bundle)
        }

        binding.toolbarDetail.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

    }


    private fun getCurrentFrag() = parentFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}")
    private fun setUp(result: com.example.movieapp.models.Result) {
        val loading = LoadingDialog(requireActivity())
        loading.startLoading()
        binding.titleDetail.text = result.title
        binding.detailContent.text = result.original_title
        val path = "https://image.tmdb.org/t/p/original${result.poster_path}"
        this.view?.let { Glide.with(it).load(path).into(binding.banner) }
        loading.isDismiss()
    }

    override fun onPause() {
        super.onPause()

    }


}