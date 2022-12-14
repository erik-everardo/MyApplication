package com.example.myapplication.features.profile.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.remote.dto.Post

class PostsAdapter(private val data: List<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentTextView: TextView = view.findViewById(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val postView = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)

        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contentTextView.text = data[position].textContent
    }

    override fun getItemCount(): Int {
        return data.size
    }


}