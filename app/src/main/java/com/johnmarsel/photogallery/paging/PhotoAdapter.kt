package com.johnmarsel.photogallery.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.johnmarsel.photogallery.R
import com.johnmarsel.photogallery.api.GalleryItem
import com.johnmarsel.photogallery.loadImage

class PhotoAdapter :
    PagingDataAdapter<GalleryItem, PhotoAdapter.PhotoHolder>(PhotoComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_gallery,
            parent, false) as ImageView
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = getItem(position)
        galleryItem?.let {
            holder.bindGalleryItem(galleryItem)
            holder.bindImage(galleryItem.url)
        }
    }

    inner class PhotoHolder(private val itemImageView: ImageView) :
        RecyclerView.ViewHolder(itemImageView), View.OnClickListener  {

        private lateinit var galleryItem: GalleryItem

        init {
            itemView.setOnClickListener(this)
        }

        val bindImage: (String) -> Unit = itemImageView::loadImage

        fun bindGalleryItem(item: GalleryItem) {
            galleryItem = item
        }
        override fun onClick(view: View) {
            CustomTabsIntent.Builder()
                .setToolbarColor(
                    ContextCompat.getColor(
                    itemImageView.context, R.color.purple_500))
                .setSecondaryToolbarColor(
                    ContextCompat.getColor(
                        itemImageView.context, R.color.purple_700)
                )
                .setShowTitle(true)
                .build()
                .launchUrl(itemImageView.context, galleryItem.photoPageUri)
        }
    }

    object PhotoComparator : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }
    }
}