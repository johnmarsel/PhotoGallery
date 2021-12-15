package com.johnmarsel.photogallery.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.johnmarsel.photogallery.FlickrFetchr
import com.johnmarsel.photogallery.api.GalleryItem

class PagingDataRepository {

    private val flickrFetchr = FlickrFetchr()

    fun fetchPagingPhotos(query: String): LiveData<PagingData<GalleryItem>> {
        return Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
            PhotoDataSource(flickrFetchr, query)
        }.liveData
    }
}