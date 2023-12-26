package com.example.movieapp.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Similar

class SimilarAdapter(var onItemClick : (Similar)->Unit?={}) : RecyclerView.Adapter<SimilarAdapter.SimilerViewHolder>() {
    inner class SimilerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val banner : ImageView = view.findViewById(R.id.movieBanner)
        private val itemLayout : LinearLayout = view.findViewById(R.id.similaerLayout)
       fun onBind(similar: Similar) {
           var url: String = "https://image.tmdb.org/t/p/original${similar.backdrop_path}"
           Glide.with(this.itemView).load(url).into(banner)
           itemLayout.setOnClickListener{
               onItemClick(similar)
           }
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilerViewHolder {
        return SimilerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.similar_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: SimilerViewHolder, position: Int) {
       holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    val differCallback = object : DiffUtil.ItemCallback<Similar>() {
        override fun areItemsTheSame(oldItem: Similar, newItem: Similar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Similar, newItem: Similar): Boolean {
           return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)


}