package com.tech.mcity.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey
    var id: Int,
    var name: String?,
    var country: String,
    var lng : Double?,
    var lat: Double?
    )
