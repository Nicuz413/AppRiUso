package com.example.appriuso

import android.location.Location
import com.google.android.gms.maps.model.LatLng

class Item(private var userUID: String?, private var name: String, private var description: String, private var image: Int, private var location: LatLng) {

    override fun toString(): String {
        return super.toString()
    }
}