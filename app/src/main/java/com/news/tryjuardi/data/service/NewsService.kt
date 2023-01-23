package com.news.tryjuardi.data.service

import com.news.tryjuardi.model.SourceResponse
import com.news.tryjuardi.model.news.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Header
interface NewsService {

    @GET("top-headlines/sources?apiKey=159add70de2a4ebc92cb6fe546f86ade")
    fun getSource(): Single<SourceResponse>
    @GET("everything?")
    fun getArticles(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int=10,
        @Query("apiKey") apiKey: String="159add70de2a4ebc92cb6fe546f86ade"
    ): Single<NewsResponse>
}