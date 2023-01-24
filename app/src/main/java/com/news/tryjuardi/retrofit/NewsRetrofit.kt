package com.news.tryjuardi.retrofit

import com.news.tryjuardi.data.service.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NewsRetrofit {
    const val BASE_URL = "https://newsapi.org/v2/"

    val retrofitClient: Retrofit.Builder by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                val request: Request =
                    original.newBuilder()
                        .header(
                            "X-Api-Key", "b961d6ebe41b4fbbabff15789b9bf873"
                        )
                        .method(original.method, original.body)
                        .build()
                chain.proceed(request)
            })
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val newsService: NewsService by lazy {
        retrofitClient.build().create(NewsService::class.java)
    }
}
