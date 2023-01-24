package com.news.tryjuardi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.tryjuardi.databinding.NewsItemBinding
import com.news.tryjuardi.model.Source
import com.news.tryjuardi.model.news.Article

class NewsAdapter(
    private val newsSelectedCallback:NewsSelectedCallback,
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var newsList = arrayListOf<Article>()
    private var context: Context? = null
    inner class ViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.item.setOnClickListener {
                newsSelectedCallback.onNewsSelected(newsList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(newsList[position]) {
                binding.tvCategory.text = title
            }
        }
    }

    override fun getItemCount() = newsList.size

    interface NewsSelectedCallback {
        fun onNewsSelected(article: Article)
    }

    fun initData(newsList: ArrayList<Article>) {
        this.newsList = newsList

    }
}