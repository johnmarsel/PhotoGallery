package com.johnmarsel.photogallery.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PhotoDeserializer : JsonDeserializer<PhotoResponse> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {
        val jsonPhotosObject = json.asJsonObject.get("photos").asJsonObject
        val totalPages = jsonPhotosObject.get("pages").asString
        val photoElements = jsonPhotosObject.getAsJsonArray("photo")
        val items = mutableListOf<GalleryItem>()

        for (element in photoElements) {
            val item: GalleryItem? = context?.deserialize(element, GalleryItem::class.java)
            item?.let {items.add(it)}
        }

        val response = PhotoResponse()
        response.galleryItems = items
        response.totalPages = totalPages
        return response
    }
}