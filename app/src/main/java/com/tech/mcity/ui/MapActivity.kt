package com.tech.mcity.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tech.mcity.R
import com.tech.mcity.data.model.City


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    var city: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        city = intent.extras?.getSerializable("city") as City

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        city?.let {

            val lat = it.lat
            val long = it.lng
            val name = it.name

            if(lat != null && long != null){

                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(lat, long))
                        .title(name)
                )

            }

        }
    }
}