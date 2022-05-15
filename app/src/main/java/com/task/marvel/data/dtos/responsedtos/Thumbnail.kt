package com.task.marvel.data.dtos.responsedtos


import com.google.gson.annotations.SerializedName


data class Thumbnail(
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("path")
    val path: String?
)