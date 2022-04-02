package com.tech.mcity.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tech.mcity.data.local.AppDatabase
import com.tech.mcity.data.local.CityDao
import com.tech.mcity.data.local.RemoteKeyDao
import com.tech.mcity.data.mediator.MainRemoteMediator
import com.tech.mcity.data.remote.Api
import javax.inject.Inject

class DefaultRepository@Inject
constructor(private val api: Api, private val appDatabase: AppDatabase, private val cityDao: CityDao, private val remoteKeyDao: RemoteKeyDao): Repository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getCity(filter: String?) = Pager(
        config = PagingConfig(pageSize = 15),
        remoteMediator = MainRemoteMediator(remoteKeyDao,cityDao,api,appDatabase,filter)
    ){
         cityDao.pagingSource()
    }.flow


}