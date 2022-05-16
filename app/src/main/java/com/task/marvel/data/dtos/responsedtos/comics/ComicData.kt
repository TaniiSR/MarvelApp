package com.task.marvel.data.dtos.responsedtos.comics


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.task.marvel.data.remote.baseclient.BaseApiResponse

@Entity(tableName = "comic_marvel")
data class ComicData(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @PrimaryKey
    @SerializedName("offset")
    val offset: Int? = null,
    @SerializedName("ts")
    var timeStamp: String? = null,
    @SerializedName("results")
    val results: List<Comic>? = null,
    @SerializedName("total")
    val total: Int? = null
) : BaseApiResponse()