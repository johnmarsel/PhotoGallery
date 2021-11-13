package com.johnmarsel.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.johnmarsel.photogallery.api.GalleryItem

class PhotoGalleryViewModel : ViewModel() {

    val galleryItemLiveData: LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()

}