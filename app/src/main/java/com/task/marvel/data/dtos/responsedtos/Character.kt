package com.task.marvel.data.dtos.responsedtos


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("comics")
    val comics: Comics?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("events")
    val events: Events?,
    @SerializedName("modified")
    val modified: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?,
    @SerializedName("series")
    val series: Series?,
    @SerializedName("stories")
    val stories: Stories?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("urls")
    val urls: List<Url>?
)