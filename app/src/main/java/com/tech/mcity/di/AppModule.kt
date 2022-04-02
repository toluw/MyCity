package com.tech.mcity.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tech.mcity.data.local.AppDatabase
import com.tech.mcity.data.local.CityDao
import com.tech.mcity.data.local.RemoteKeyDao
import com.tech.mcity.data.remote.Api
import com.tech.mcity.data.repository.DefaultRepository
import com.tech.mcity.data.repository.Repository
import com.tech.mcity.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder() : Gson {
        return GsonBuilder().create()

    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkhttp(logging: HttpLoggingInterceptor): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(logging)
            .callTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .connectTimeout(10, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okkHttpclient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder()
            .client(okkHttpclient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)

    }


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit.Builder): Api {
        return retrofit
            .build()
            .create(Api::class.java)
    }


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            //    .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCityDao(appDatabase: AppDatabase): CityDao {
        return  appDatabase.getCityDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeyDao(appDatabase: AppDatabase): RemoteKeyDao {
        return  appDatabase.getRemoteKeyDao()
    }


    @Singleton
    @Provides
    fun provideRepository(api: Api, appDatabase: AppDatabase, cityDao: CityDao, remoteKeyDao: RemoteKeyDao): Repository {
        return DefaultRepository(api,appDatabase,cityDao,remoteKeyDao)
    }





}