package com.tech.mcity.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("items") val items : List<Items>,
    @SerializedName("pagination") val pagination : Pagination
)
