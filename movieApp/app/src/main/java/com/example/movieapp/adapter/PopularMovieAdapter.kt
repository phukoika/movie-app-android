package com.example.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Result

class PopularMovieAdapter(private val onItemClick : (com.example.movieapp.models.Result)->Unit) : RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    inner class PopularMovieViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val banner : ImageView = view.findViewById(R.id.banner)
        private val itemLayout : ConstraintLayout = view.findViewById(R.id.movie_item)
        public fun onBind(result: com.example.movieapp.models.Result){
            var url : String = "https://image.tmdb.org/t/p/original${result.backdrop_path}"
            Glide.with(this.itemView).load(url).into(banner)
            itemLayout.setOnClickListener {
                onItemClick(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        return PopularMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        val movies = differ.currentList[position]
        holder.onBind(movies)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private val differCallBack = object : DiffUtil.ItemCallback<com.example.movieapp.models.Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this,differCallBack)
}