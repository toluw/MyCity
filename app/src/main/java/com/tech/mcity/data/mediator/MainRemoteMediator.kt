package com.tech.mcity.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tech.mcity.data.local.AppDatabase
import com.tech.mcity.data.local.CityDao
import com.tech.mcity.data.local.RemoteKeyDao
import com.tech.mcity.data.model.City
import com.tech.mcity.data.model.RemoteKey
import com.tech.mcity.data.remote.Api
import com.tech.mcity.utils.getCityFromNetworkResponse
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MainRemoteMediator(
    private val remoteKeyDao: RemoteKeyDao,
    private val cityDao: CityDao,
    private val api: Api,
    private val database: AppDatabase,
    private val filter: String?
    ): RemoteMediator<Int, City>() {


    override suspend fun load(loadType: LoadType, state: PagingState<Int, City>): MediatorResult {

        return try {

            val loadKey: Int? = when(loadType){

                LoadType.REFRESH -> null

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {

                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByQuery()
                    }

                    if(remoteKey.nextPage == null){
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextPage

                }
            }

            val response = api.getCity(loadKey.toString(),filter)

            val currentPage = response.body()!!.data!!.pagination.currentPage
            val lastPage = response.body()!!.data!!.pagination.lastPage

            val nextPage = if(currentPage == lastPage){
                null
            }else{
                currentPage?.plus(1)
            }

            database.withTransaction {

                if(loadType == LoadType.REFRESH){
                    remoteKeyDao.deleteByQuery()
                    cityDao.clearAll()
                }

                remoteKeyDao.insertOrReplace(RemoteKey(nextPage))

                cityDao.insertAll(getCityFromNetworkResponse(response.body()!!))

            }

            MediatorResult.Success(
                endOfPaginationReached = nextPage == null
            )

        }catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }


    }


}