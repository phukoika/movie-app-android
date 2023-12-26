package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.models.Result

class LikedAdapter : RecyclerView.Adapter<LikedAdapter.LikedViewHolder>() {
    inner class LikedViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun onBind(result: com.example.movieapp.models.Result){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedViewHolder {
        return LikedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.liked_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: LikedViewHolder, position: Int) {
        holder.onBind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    val differCallback = object : DiffUtil.ItemCallback<com.example.movieapp.models.Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}