package com.tech.mcity.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tech.mcity.data.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys")
    suspend fun remoteKeyByQuery(): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun deleteByQuery()

}