package com.task.marvel.data.dtos.responsedtos.characters


import com.google.gson.annotations.SerializedName
import com.task.marvel.data.remote.baseclient.BaseApiResponse


data class CharactersResponse(
    @SerializedName("attributionHTML")
    val attributionHTML: String?,
    @SerializedName("attributionText")
    val attributionText: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("data")
    val data: CharacterData?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("status")
    val status: String?
) : BaseApiResponse()