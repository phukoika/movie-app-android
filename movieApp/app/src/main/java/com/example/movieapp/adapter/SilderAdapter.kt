package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R

class SilderAdapter(private val sliderList : ArrayList<Int>,private val viewPager2: ViewPager2) : RecyclerView.Adapter<SilderAdapter.SliderViewHolder>() {

    class SliderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageView : ImageView = view.findViewById(R.id.sliderImg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.slider_container,parent,false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.imageView.setImageResource(sliderList[position])
        if(position == sliderList.size - 1 ){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
       return sliderList.size
    }
    private val runnable = Runnable {
        sliderList.addAll(sliderList)
        notifyDataSetChanged()
    }

}