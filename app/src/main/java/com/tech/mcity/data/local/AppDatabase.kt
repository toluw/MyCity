package com.tech.mcity.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tech.mcity.data.model.City
import com.tech.mcity.data.model.RemoteKey

@Database(entities = [City::class,RemoteKey::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getCityDao(): CityDao

    abstract fun getRemoteKeyDao(): RemoteKeyDao

    companion object{
        val DATABASE_NAME = "mcity_app_db"
    }

}