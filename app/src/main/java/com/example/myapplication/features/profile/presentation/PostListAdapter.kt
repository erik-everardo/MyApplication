package com.example.myapplication.features.profile.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.remote.dto.PostDataDto
import com.example.myapplication.databinding.PostBinding
import io.noties.markwon.Markwon

class PostListAdapter() : ListAdapter<PostDataDto, PostListAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private val binding: PostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostDataDto) {
            binding.username.text = post.userName

            val markdown = Markwon.create(binding.content.context)
            markdown.setMarkdown(binding.content, post.post.textContent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListAdapter.ViewHolder {
        return ViewHolder(PostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PostDataDto>() {
            override fun areItemsTheSame(oldItem: PostDataDto, newItem: PostDataDto): Boolean {
                return oldItem.post.id == newItem.post.id
            }

            override fun areContentsTheSame(oldItem: PostDataDto, newItem: PostDataDto): Boolean {
                return oldItem == newItem
            }

        }
    }
}