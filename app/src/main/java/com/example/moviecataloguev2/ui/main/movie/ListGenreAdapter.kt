package com.example.moviecataloguev2.ui.main.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecataloguev2.databinding.ItemListGenreBinding
import javax.inject.Inject

class ListGenreAdapter @Inject constructor() :
    ListAdapter<String, ListGenreAdapter.ViewHolder>(diffUtils){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListGenreBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.binding.genre = getItem(position)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(itemBinding: ItemListGenreBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemListGenreBinding = itemBinding
    }

    companion object {
        private val diffUtils = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem== newItem
        }
    }
}