package com.example.movieapp.ui.fragment.MainAcivityContent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide.init
import com.example.movieapp.MoviesApplication
import com.example.movieapp.R
import com.example.movieapp.adapter.PopularMovieAdapter
import com.example.movieapp.adapter.SilderAdapter
import com.example.movieapp.databinding.FragmentHomePageBinding
import com.example.movieapp.db.MoviesDatabase
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.utils.LoadingDialog
import com.example.movieapp.utils.Resource
import com.example.movieapp.viewmodels.MovieViewModel
import com.example.movieapp.viewmodels.MoviesViewModelProviderFactory
import java.lang.Math.abs


class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var adapterPopular : PopularMovieAdapter
    private lateinit var adapterTopRate : PopularMovieAdapter
    lateinit var viewModel: MovieViewModel

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var sliderList: ArrayList<Int>
    private lateinit var adapter: SilderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addEvents()
        initSlider()
        setUpTransForm()
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)

            }
        })
    }
    private val onClickItem : (com.example.movieapp.models.Result)->Unit = {
        val bundle = bundleOf(
            "result" to it
        )
        findNavController().navigate(R.id.action_homePageFragment_to_detailFragment,bundle)

    }
    private fun addEvents() {
        val loading = LoadingDialog(requireActivity())
        adapterPopular = PopularMovieAdapter(onClickItem)
        binding.rvPopular.apply {
            this.adapter = adapterPopular
            this.set3DItem(true)
            this.setAlpha(false)
            this.setInfinite(true)
        }
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    it.data?.let { NewResponse->
                        adapterPopular.differ.submitList(NewResponse.results.toList())
                    }
                    binding.loadingItem.visibility =View.GONE
                }
                is Resource.Error -> {
                    it.message?.let{ message->
                        Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    binding.loadingItem.visibility =View.VISIBLE
                }

            }
        }
        adapterTopRate = PopularMovieAdapter(onClickItem)
        binding.rvTopRated.apply {
            this.adapter = adapterTopRate

        }
        viewModel.topRated.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    it.data?.let { NewResponse->
                        adapterTopRate.differ.submitList(NewResponse.results.toList())
                    }
                }
                is Resource.Error -> {
                    it.message?.let{ message->
                        Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    Toast.makeText(requireContext(),"Loading",Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    private fun init() {
        val newRepo = MoviesRepository(MoviesDatabase(requireContext()))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(MoviesApplication(),newRepo)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MovieViewModel::class.java]
    }


    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }
    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransForm() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer {page,position ->
            val r = 1 - abs(position)
            page.scaleX = 1f - (1f - 0.8f) * abs(position)
           page.scaleY = 1f - (1f - 0.8f) * abs(position)
          page.alpha = 1.0f - (1.0f - 0.3f) * abs(position)
        }
        viewPager2.setPageTransformer(transformer)
    }

    private fun initSlider() {
        viewPager2 = binding.viewSlider
        handler =  Handler(Looper.myLooper()!!)
        sliderList = ArrayList()
        sliderList.add(R.drawable.slider1)
        sliderList.add(R.drawable.slider2)
        sliderList.add(R.drawable.slider3)

        adapter = SilderAdapter(sliderList,viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
}