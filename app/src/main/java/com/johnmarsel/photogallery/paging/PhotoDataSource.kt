package com.johnmarsel.photogallery.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.johnmarsel.photogallery.FlickrFetchr
import com.johnmarsel.photogallery.api.GalleryItem
import retrofit2.HttpException
import java.io.IOException

class PhotoDataSource(private val flickrFetchr: FlickrFetchr, private val query: String) :
    PagingSource<Int, GalleryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {

        val page = params.key ?: 1
        return try {

            val response = flickrFetchr.fetchPhotoMetaData(page, query, params.loadSize)

            LoadResult.Page(
                response.galleryItems,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page < response.totalPages.toInt()) page + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
