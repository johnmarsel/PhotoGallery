package com.johnmarsel.photogallery.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnmarsel.photogallery.databinding.LoadingErrorStateBinding
import com.johnmarsel.photogallery.visible

class PhotosLoadStateAdapter(private val retry: () -> Unit):
    LoadStateAdapter<PhotosLoadStateAdapter.PhotosLoadStateViewHolder>() {

    inner class PhotosLoadStateViewHolder(private val binding: LoadingErrorStateBinding,
                                          private val retry: () -> Unit):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }
            binding.progressbar.visible(loadState is LoadState.Loading)
            binding.buttonRetry.visible(loadState is LoadState.Error)
            binding.textViewError.visible(loadState is LoadState.Error)
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: PhotosLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState)
    = PhotosLoadStateViewHolder(
        LoadingErrorStateBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), retry)
}