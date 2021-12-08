package com.johnmarsel.photogallery

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.johnmarsel.photogallery.api.FlickrApi
import com.johnmarsel.photogallery.api.GalleryItem
import com.johnmarsel.photogallery.paging.PhotoDataSource

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {

    private val flickrFetchr = FlickrFetchr()

    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    var galleryItemLiveData: LiveData<PagingData<GalleryItem>>

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            Pager(PagingConfig(pageSize = 20)) {
                PhotoDataSource(flickrFetchr, searchTerm)
            }.liveData.cachedIn(viewModelScope)
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}