package com.example.a22byte

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a22byte.data.ApiService
import com.example.a22byte.data.News
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsAdapter(private val context: Context, private val callback: (Int) -> Unit) :
    ListAdapter<News, NewsAdapter.ArticleViewHolder>(NewsListDiffCallback()) {

    var onArticleClickListener: ((String) -> Unit)? = null

    class ArticleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_tv)
        val description: TextView = view.findViewById(R.id.description_tv)
        val publishDate: TextView = view.findViewById(R.id.publish_date_tv)
        val image: com.google.android.material.imageview.ShapeableImageView =
            view.findViewById(R.id.article_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.news_list_item,
            parent,
            false
        )
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val news = getItem(position)
        Log.d("item", news.toString())
        holder.title.text = news.title
        holder.description.text = news.description
        holder.publishDate.text = parseDate(news.publishedAt) //распарсить дату

        holder.view.setOnClickListener {
            onArticleClickListener?.invoke(news.url)
        }

        Glide.with(context)
            .load(news.urlToImage)
            .into(holder.image)

        if (itemCount - position == 1) {
            callback(itemCount / ApiService.pageSize + 1) // счет страниц не с 0
            Log.d("paging", "$itemCount, ${itemCount/ApiService.pageSize + 1}")
        }
    }


    private fun parseDate(date: String): String {
        Log.d("date", date.substringBefore('T'))
        val parsedDate =
            LocalDate.parse(date.substringBefore('T'), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return "${parsedDate.dayOfMonth} ${parsedDate.month} ${parsedDate.year}"
    }

    fun loadNextPage(nextPage: List<News>?) {
        if (!nextPage.isNullOrEmpty()) {
            val allArticles = currentList + nextPage
            submitList(allArticles)
        }
    }
}