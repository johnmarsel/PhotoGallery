package com.johnmarsel.photogallery.paging

import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.johnmarsel.photogallery.api.GalleryItem

class PhotosAdapter :
    PagingDataAdapter<GalleryItem, PhotosAdapter.PhotosViewHolder>(PhotosComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosViewHolder {
        val textView = TextView(parent.context)
        return PhotosViewHolder(textView)
    }
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val galleryItem = getItem(position)
        galleryItem?.let { holder.bindTitle(it.title) }
    }

    inner class PhotosViewHolder(itemTextView: TextView) :
        RecyclerView.ViewHolder(itemTextView) {

        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    object PhotosComparator : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }
    }
}