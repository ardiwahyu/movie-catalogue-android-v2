package com.example.moviecataloguev2.ui.main.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecataloguev2.BuildConfig.BASE_URL_IMAGE
import com.example.moviecataloguev2.data.remote.model.Movie
import com.example.moviecataloguev2.databinding.ItemListMovieBinding
import javax.inject.Inject

class ListMovieAdapter @Inject constructor() :
    ListAdapter<Movie, ListMovieAdapter.ViewHolder>(diffUtils){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val movie = getItem(position)
            holder.binding.movie = movie
            Glide.with(holder.binding.ivPoster.context)
                .load(BASE_URL_IMAGE+"w500/"+movie.posterPath)
                .transform(RoundedCorners(16))
                .into(holder.binding.ivPoster)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(itemBinding: ItemListMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemListMovieBinding = itemBinding
    }

    companion object {
        private val diffUtils = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.title == newItem.title
        }
    }
}