package com.tech.mcity.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tech.mcity.data.model.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(city: List<City>)


    @Query("SELECT * FROM city")
    fun pagingSource(): PagingSource<Int, City>


    @Query("DELETE FROM city")
    suspend fun clearAll()

}