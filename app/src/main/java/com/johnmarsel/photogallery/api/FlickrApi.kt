package com.johnmarsel.photogallery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=6a17d70fedd86896508b8d4cb407af6f" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    fun fetchPhotos(): Call<PhotoResponse>
}