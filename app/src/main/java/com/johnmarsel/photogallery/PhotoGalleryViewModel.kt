package com.johnmarsel.photogallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.johnmarsel.photogallery.api.FlickrApi
import com.johnmarsel.photogallery.paging.PhotoDataSource

class PhotoGalleryViewModel : ViewModel() {

    private val flickrApi = FlickrApi.create()

    val galleryItemLiveData = Pager(PagingConfig(pageSize = 100)) {
        PhotoDataSource(flickrApi)
    }.liveData.cachedIn(viewModelScope)
}