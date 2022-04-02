package com.tech.mcity.utils

import com.tech.mcity.data.model.City
import com.tech.mcity.data.model.CityResponse

fun getCityFromNetworkResponse(cityResponse: CityResponse):List<City>{

    val result = ArrayList<City>()

    val items =  cityResponse.data!!.items

    for(item in items){

        val city = City(item.id!!,item.name,item.country?.name,item.lng,item.lat)
        result.add(city)

    }

    return result

}