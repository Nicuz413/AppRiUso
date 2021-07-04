package com.example.appriuso

data class Item(private val userUID: String? = null, private var itemName: String? = null, private var itemDescription: String? = null, private var itemImage: String? = null, private var itemLatitude: Double? = null, private var itemLongitude: Double? = null, private var itemType: String? = null) {

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
    public fun getItemLatitude(): Double? {
        return itemLatitude
    }
    public fun getItemLongitude(): Double? {
        return itemLongitude
    }
    public fun getItemType(): String? {
        return itemType
    }
}