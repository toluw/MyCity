package com.tech.mcity.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id") val id : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("code") val code : String?,
    @SerializedName("created_at") val createdAt : String?,
    @SerializedName("updated_at") val updatedAt : String?,
    @SerializedName("continent_id") val continentId : Int?
)
