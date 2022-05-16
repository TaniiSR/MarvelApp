package com.task.marvel.data.dtos.responsedtos.comics


import com.google.gson.annotations.SerializedName
import com.task.marvel.data.dtos.responsedtos.characters.Item


data class Creators(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("returned")
    val returned: Int?
)