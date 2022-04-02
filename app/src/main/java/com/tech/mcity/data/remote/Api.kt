package com.tech.mcity.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("v1/city")
    suspend fun getCity(@Query("page",encoded = true)page: String?,@Query("filter[0][name][contains]",encoded = true)filter:String?,@Query("include")include:String = "country")

}