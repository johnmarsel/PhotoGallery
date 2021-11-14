package com.johnmarsel.photogallery.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.Exception
import java.lang.reflect.Type

class PhotoDeserializer : JsonDeserializer<PhotoResponse> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {
        val jsonObject = json.asJsonObject.get("photos").asJsonObject
        val photoElements = jsonObject.getAsJsonArray("photo")
        val items = mutableListOf<GalleryItem>()

        for (element in photoElements) {
            val item: GalleryItem? = context?.deserialize(element, GalleryItem::class.java)
            if (item != null) {
                items.add(item)
            }
        }
        val response = PhotoResponse()
        response.galleryItems = items

        return response
    }
}