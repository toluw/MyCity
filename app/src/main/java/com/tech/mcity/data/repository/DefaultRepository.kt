package com.tech.mcity.data.repository

import com.tech.mcity.data.local.AppDatabase
import com.tech.mcity.data.local.CityDao
import com.tech.mcity.data.local.RemoteKeyDao
import com.tech.mcity.data.remote.Api
import javax.inject.Inject

class DefaultRepository@Inject
constructor(private val api: Api, private val appDatabase: AppDatabase, private val cityDao: CityDao, private val remoteKeyDao: RemoteKeyDao): Repository {



}