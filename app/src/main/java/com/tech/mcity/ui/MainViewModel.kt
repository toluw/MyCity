package com.tech.mcity.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tech.mcity.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repository: Repository, private val savedStateHandle: SavedStateHandle): ViewModel(){

    companion object{

        val DEFAULT_FILTER: String? = null

        const val FILTER_KEY = "main_filter"
    }

    init {

        if(!savedStateHandle.contains(FILTER_KEY)){

            savedStateHandle.set(FILTER_KEY, DEFAULT_FILTER)

        }

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val myCity = savedStateHandle.getLiveData<String?>(FILTER_KEY)
        .asFlow()
        .flatMapLatest { repository.getCity(it) }
        .cachedIn(viewModelScope)



     fun filterCity(filter: String?){

        savedStateHandle.set(FILTER_KEY,filter)

     }
}