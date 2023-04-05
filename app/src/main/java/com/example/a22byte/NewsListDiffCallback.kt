package com.example.a22byte

import androidx.recyclerview.widget.DiffUtil
import com.example.a22byte.data.News

class NewsListDiffCallback: DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title == newItem.title
    }
}