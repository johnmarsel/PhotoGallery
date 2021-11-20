package com.johnmarsel.photogallery.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(@Query("page") page: Int,
                            @Query("per_page") perPage: Int): PhotoResponse

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("page") page: Int,
                             @Query("text") query: String): PhotoResponse

    companion object {

        private const val BASE_URL = "https://api.flickr.com/"

        fun create(): FlickrApi {
            val gson = GsonBuilder()
                .registerTypeAdapter(PhotoResponse::class.java, PhotoDeserializer())
                .create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().also { client ->
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                    client.addInterceptor(PhotoInterceptor())
                }.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(FlickrApi::class.java)
        }
    }
}