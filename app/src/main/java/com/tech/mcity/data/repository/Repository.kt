package com.tech.mcity.data.repository

import androidx.paging.PagingData
import com.tech.mcity.data.model.City
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCity(filter: String?): Flow<PagingData<City>>
}