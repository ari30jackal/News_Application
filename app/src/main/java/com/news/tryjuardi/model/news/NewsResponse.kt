package com.news.tryjuardi.model.news

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)