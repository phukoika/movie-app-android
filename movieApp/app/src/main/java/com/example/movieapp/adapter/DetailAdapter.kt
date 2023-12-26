package com.example.movieapp.adapter

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movieapp.ui.fragment.deitailTab.CastFragment
import com.example.movieapp.ui.fragment.deitailTab.MoreFragment
import com.example.movieapp.ui.fragment.deitailTab.TrailerFragment
import com.google.gson.Gson


class DetailAdapter(
                    fm: FragmentManager,
                    lifecycle: Lifecycle,val result: com.example.movieapp.models.Result
) : FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val bundle = bundleOf(
                    "id" to result.id.toString()
                )

                return TrailerFragment().apply {
                    this.arguments = bundle
                }
            }
            1 -> {
                val bundle = bundleOf(
                    "id" to result.id.toString()
                )
//                Log.d("123",result.id.toString())
                return CastFragment().apply {
                    this.arguments = bundle
                }
            }
            2 ->{
                val bundle = bundleOf(
                    "id" to result.id.toString()
                )
              return MoreFragment().apply {
                  this.arguments = bundle
              }
            }
            else -> throw Resources.NotFoundException("Not Found")
        }

    }

}