package com.johnmarsel.photogallery

import androidx.paging.PagingSource
import com.johnmarsel.photogallery.api.FlickrApi
import com.johnmarsel.photogallery.api.PhotoResponse
import retrofit2.Call

private const val TAG = "FlickrFetchr"

class FlickrFetchr {

    private val flickrApi: FlickrApi = FlickrApi.get()

    fun fetchPhotosRequest(): Call<PhotoResponse> {
        return flickrApi.fetchPhotosForWorker()
    }

    fun searchPhotosRequest(query: String): Call<PhotoResponse> {
        return flickrApi.searchPhotosForWorker(query)
    }

    suspend fun fetchPhotoMetaData(page: Int,
                                   query: String,
                                   params: PagingSource.LoadParams<Int>
    ): PhotoResponse {
        return when(query.isBlank())  {
            true -> flickrApi.fetchPhotos(page, params.loadSize)
            else -> {
                flickrApi.searchPhotos(page, query)
            }
        }
    }
}