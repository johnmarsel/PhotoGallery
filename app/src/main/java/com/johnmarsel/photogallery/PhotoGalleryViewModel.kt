package com.johnmarsel.photogallery

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.*
import com.johnmarsel.photogallery.api.GalleryItem
import com.johnmarsel.photogallery.paging.PagingDataRepository
import com.johnmarsel.photogallery.paging.PhotoDataSource

class PhotoGalleryViewModel(private val app: Application) : AndroidViewModel(app) {

    private val pagingRepository = PagingDataRepository()

    private val mutableSearchTerm = MutableLiveData<String>()
    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    var galleryItemLiveData: LiveData<PagingData<GalleryItem>>

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            pagingRepository.fetchPagingPhotos(searchTerm)
                .cachedIn(viewModelScope)
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }
}