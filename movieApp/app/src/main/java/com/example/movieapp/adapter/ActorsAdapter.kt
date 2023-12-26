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
import com.example.movieapp.models.Cast

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {
    inner class ActorsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val avatar : ImageView = view.findViewById(R.id.actors_ava)
        private val name : TextView = view.findViewById(R.id.actors_name)
        private val descrip : TextView = view.findViewById(R.id.actors_description)
       fun onBind(cast: Cast){
           var url : String = "https://image.tmdb.org/t/p/original${cast.profile_path}"
           Glide.with(this.itemView).load(url).into(avatar)
           name.text = cast.name
           descrip.text = cast.known_for_department

       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
       return ActorsViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.actors_item,parent,false)
       )
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val value = differ.currentList[position]
        holder.onBind(value)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private val differCallback = object : DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}