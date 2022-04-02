package com.tech.mcity.data.model

import com.google.gson.annotations.SerializedName

data class Items(

    @SerializedName("id") val id : Int?,
    @SerializedName("name") val name : String?,
    @SerializedName("local_name") val localName : String?,
    @SerializedName("lat") val lat : Double?,
    @SerializedName("lng") val lng : Double?,
    @SerializedName("created_at") val createdAt : String?,
    @SerializedName("updated_at") val updatedAt : String?,
    @SerializedName("country_id") val countryId : Int?,
    @SerializedName("country")val country: Country?
)
