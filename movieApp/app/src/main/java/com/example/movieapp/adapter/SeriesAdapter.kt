package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Series

class SeriesAdapter(val onClickItem : (Series)->Unit? = {}) : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {
    inner class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val banner : ImageView = view.findViewById(R.id.movieBanner)
        private val itemLayout : LinearLayout = view.findViewById(R.id.similaerLayout)
        fun onBind(series: Series){
            var url : String = "https://image.tmdb.org/t/p/original${series.poster_path}"
            Glide.with(this.itemView).load(url).into(banner)
            itemLayout.setOnClickListener{
                onClickItem(series)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.similar_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }

    val differCallback = object : DiffUtil.ItemCallback<Series>(){
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}