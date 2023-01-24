package com.news.tryjuardi.data.service

import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.NewsResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Header

interface NewsService {

    @GET("top-headlines/sources?")
    fun getSource(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = "b961d6ebe41b4fbbabff15789b9bf873"
    ): Single<SourceResponse>

    @GET("everything?")
    fun getArticles(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = "b961d6ebe41b4fbbabff15789b9bf873"
    ): Single<NewsResponse>

    @GET("everything?")
    fun getByKeyword(
        @Query("q") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = "b961d6ebe41b4fbbabff15789b9bf873"
    ): Single<NewsResponse>
}