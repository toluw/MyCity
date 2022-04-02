package com.tech.mcity.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(

    val nextPage: Int?,
    @PrimaryKey
    var id: Int = 1

)
