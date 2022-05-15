package com.task.marvel.data.dtos.responsedtos


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.task.marvel.data.remote.baseclient.BaseApiResponse

@Entity(tableName = "character_marvel")
data class CharacterData(
    @PrimaryKey
    @SerializedName("offset")
    val offset: Int? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("ts")
    var timeStamp: String? = null,
    @SerializedName("results")
    val results: List<Character>? = null,
    @SerializedName("total")
    val total: Int? = null
) : BaseApiResponse()