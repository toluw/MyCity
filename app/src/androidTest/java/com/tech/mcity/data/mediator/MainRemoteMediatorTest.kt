package com.tech.mcity.data.mediator

import FakeApi
import android.content.ClipData
import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tech.mcity.data.local.AppDatabase
import com.tech.mcity.data.local.CityDao
import com.tech.mcity.data.local.RemoteKeyDao
import com.tech.mcity.data.model.*
import com.tech.mcity.data.remote.Api
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainRemoteMediatorTest{

    val context = ApplicationProvider.getApplicationContext<Context>()
    var api = FakeApi()
    var db: AppDatabase =   Room.inMemoryDatabaseBuilder(
        context, AppDatabase::class.java).build()

    private lateinit var cityDao: CityDao
    private lateinit var remoteKeyDao: RemoteKeyDao



    @Before
    fun setup(){

        cityDao = db.getCityDao()

        remoteKeyDao = db.getRemoteKeyDao()

    }


    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
        api.clearCity()
        api.failureMsg = null

    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest{

        val pagination = Pagination(1,10,10,160)
        val item1 = Items(1,"j","j",3.0,4.0,"ut","ma", 9, Country(1,"yd","yt","yr","yr",6))
        val item2 = Items(2,"j","j",3.0,4.0,"ut","ma", 9, Country(3,"yd","yt","yr","yr",6))
        val data = Data(listOf(item1,item2),pagination)
        val cityResponse = CityResponse(data,6)

        api.addCity(cityResponse)

        val mediator = MainRemoteMediator(remoteKeyDao,cityDao,api,db,"yea")

        val pagingState = PagingState<Int, City>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = mediator.load(LoadType.REFRESH, pagingState)

        assert( result is RemoteMediator.MediatorResult.Success )

        assert(!(result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)


    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest{

        val pagination = Pagination(10,10,10,160)
        val item1 = Items(1,"j","j",3.0,4.0,"ut","ma", 9, Country(1,"yd","yt","yr","yr",6))
        val item2 = Items(2,"j","j",3.0,4.0,"ut","ma", 9, Country(3,"yd","yt","yr","yr",6))
        val data = Data(listOf(item1,item2),pagination)
        val cityResponse = CityResponse(data,6)

        api.addCity(cityResponse)

        val mediator = MainRemoteMediator(remoteKeyDao,cityDao,api,db,"yea")

        val pagingState = PagingState<Int, City>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = mediator.load(LoadType.REFRESH, pagingState)

        assert( result is RemoteMediator.MediatorResult.Success )

        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

    }


    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {

        api.failureMsg = "Network Error occured"

        val pagination = Pagination(10,10,10,160)
        val item1 = Items(1,"j","j",3.0,4.0,"ut","ma", 9, Country(1,"yd","yt","yr","yr",6))
        val item2 = Items(2,"j","j",3.0,4.0,"ut","ma", 9, Country(3,"yd","yt","yr","yr",6))
        val data = Data(listOf(item1,item2),pagination)
        val cityResponse = CityResponse(data,6)

        api.addCity(cityResponse)

        val mediator = MainRemoteMediator(remoteKeyDao,cityDao,api,db,"yea")

        val pagingState = PagingState<Int, City>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = mediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)


    }



}