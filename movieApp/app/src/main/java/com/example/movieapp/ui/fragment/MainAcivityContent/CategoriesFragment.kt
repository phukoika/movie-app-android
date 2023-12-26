package com.example.movieapp.ui.fragment.MainAcivityContent

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.adapter.CategoriesAdapter
import com.example.movieapp.databinding.FragmentCategoriesBinding
import com.example.movieapp.viewmodels.MovieViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_detail.*


class CategoriesFragment : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var viewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabCate.addTab(tabCate.newTab().setText("Movie"))
        binding.tabCate.addTab(tabCate.newTab().setText("Series"))
        categoriesAdapter = CategoriesAdapter(parentFragmentManager,lifecycle)
        Handler(Looper.getMainLooper()).post {
            // interact with ViewPager here
            binding.viewPageMovies.adapter = categoriesAdapter
            TabLayoutMediator(binding.tabCate,binding.viewPageMovies){tab,position->
                tab.text = when(position){
                    0->"Movie"
                    1->"Series"
                    else -> Resources.NotFoundException("Not found").toString()
                }
            }.attach()
        }





    }



}