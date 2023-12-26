package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.models.Review
import java.util.Objects

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private var avatar : ImageView = view.findViewById(R.id.user_ava)
        private var name : TextView = view.findViewById(R.id.user_name)
        private var comment : TextView = view.findViewById(R.id.user_comment)
        fun onBind(review: Review){
            var url : String = "https://image.tmdb.org/t/p/original${review.author_details.avatar_path}"
            Glide.with(this.itemView).load(url).into(avatar)
            name.text = review.author.toString()
            comment.text = review.content.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<Review>(){
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}