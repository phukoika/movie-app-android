package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Result

class MovieAdapter(val onItemClick : (com.example.movieapp.models.Result)->Unit? = {}) : RecyclerView.Adapter<MovieAdapter.MovieViewHoler>(){
    inner class MovieViewHoler(view: View) : RecyclerView.ViewHolder(view){
        private val banner : ImageView = view.findViewById(R.id.movieBanner)
        private val itemLayout : LinearLayout = view.findViewById(R.id.similaerLayout)
        fun onBind(result: com.example.movieapp.models.Result){
            var url : String = "https://image.tmdb.org/t/p/original${result.poster_path}"
            Glide.with(this.itemView).load(url).into(banner)
            itemLayout.setOnClickListener{
                onItemClick(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHoler {
        return MovieViewHoler(
            LayoutInflater.from(parent.context).inflate(R.layout.similar_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHoler, position: Int) {
       holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    val differCallback = object : DiffUtil.ItemCallback<com.example.movieapp.models.Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
    public val differ = AsyncListDiffer(this,differCallback)
}