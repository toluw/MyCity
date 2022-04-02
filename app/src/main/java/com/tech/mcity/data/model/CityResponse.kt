package com.tech.mcity.data.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("data") val data : Data?,
    @SerializedName("time") val time : Int?
)
