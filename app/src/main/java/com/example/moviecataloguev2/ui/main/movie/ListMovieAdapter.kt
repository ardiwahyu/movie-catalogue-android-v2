package com.example.moviecataloguev2.ui.main.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecataloguev2.BuildConfig.BASE_URL_IMAGE
import com.example.moviecataloguev2.data.remote.model.Movie
import com.example.moviecataloguev2.databinding.ItemListLoadingBinding
import com.example.moviecataloguev2.databinding.ItemListMovieBinding
import com.example.moviecataloguev2.databinding.PlaceholderListMovieBinding
import javax.inject.Inject

class ListMovieAdapter @Inject constructor() :
    ListAdapter<Movie, RecyclerView.ViewHolder>(diffUtils){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemListMovieBinding.inflate(LayoutInflater.from(parent.context))
            ViewHolder(binding)
        } else {
            val binding = ItemListLoadingBinding.inflate(LayoutInflater.from(parent.context))
            LoadingViewHolder(binding)
        }
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        try {
            if (holder is ViewHolder) {
                val movie = getItem(position)
                holder.binding.movie = movie
                Glide.with(holder.binding.ivPoster.context)
                    .load(BASE_URL_IMAGE + "w500/" + movie.posterPath)
                    .transform(RoundedCorners(16))
                    .into(holder.binding.ivPoster)

                val listGenreAdapter = ListGenreAdapter()
                holder.binding.rvListGenre.layoutManager = LinearLayoutManager(
                    holder.binding.rvListGenre.context,
                    LinearLayoutManager.HORIZONTAL, false
                )
                holder.binding.rvListGenre.adapter = listGenreAdapter
                listGenreAdapter.submitList(movie.listGenreString)
            } else if (holder is LoadingViewHolder) {
                holder.binding.sflLoading.startShimmer()
            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    class ViewHolder(itemBinding: ItemListMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemListMovieBinding = itemBinding
    }

    class LoadingViewHolder(itemBinding: ItemListLoadingBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemListLoadingBinding = itemBinding
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1

        private val diffUtils = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.title == newItem.title
        }
    }
}