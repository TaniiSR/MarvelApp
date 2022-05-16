package com.task.marvel.data.dtos.responsedtos.characters


import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("returned")
    val returned: Int?
)