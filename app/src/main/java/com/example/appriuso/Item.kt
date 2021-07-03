package com.example.appriuso

import com.google.android.gms.maps.model.LatLng

data class Item(private val userUID: String? = null, private var itemName: String? = null, private var itemDescription: String? = null, private var itemImage: String? = null, private var itemLocation: LatLng? = null) {
    public fun getUserUID(): String? {
        return userUID
    }
    public fun getItemName(): String? {
        return itemName
    }
    public fun getItemDescription(): String? {
        return itemDescription
    }
    public fun getItemImage(): String? {
        return itemImage
    }
    public fun getItemLocation(): LatLng? {
        return itemLocation
    }
}