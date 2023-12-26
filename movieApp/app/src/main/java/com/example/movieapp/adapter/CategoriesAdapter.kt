package com.example.movieapp.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.ui.fragment.Categories.MovieListFragment
import com.example.movieapp.ui.fragment.Categories.SeriesFragment

class CategoriesAdapter(fm: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> {
                return MovieListFragment()
            }
            1 ->{
                return SeriesFragment()
            }
            else -> throw Resources.NotFoundException("Not Found")
        }
    }

}