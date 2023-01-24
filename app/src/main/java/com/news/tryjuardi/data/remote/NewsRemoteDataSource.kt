package com.news.tryjuardi.data.remote

import android.util.Log
import com.news.tryjuardi.data.service.NewsService
import com.news.tryjuardi.model.SourceResponse
import io.reactivex.Single
import javax.inject.Inject
import androidx.paging.PageKeyedDataSource
import com.news.tryjuardi.model.news.Article
import com.news.tryjuardi.model.news.NewsResponse


class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
) {

    fun getSource(category: String, page: Int): Single<SourceResponse> {
        return newsService.getSource(category, page)
    }

    fun getArticles(sources: String, page: Int): Single<NewsResponse> {
        return newsService.getArticles(sources, page)
    }

    fun getByKeyword(sources: String, page: Int): Single<NewsResponse> {
        return newsService.getByKeyword(sources, page)
    }
}