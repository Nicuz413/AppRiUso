package com.example.appriuso

data class Item(private val itemKey: String? = null, private val userUID: String? = null, private var itemName: String? = null, private var itemDescription: String? = null, private var itemImage: String? = null, private var itemPositionAddress: String? = null, private var itemType: String? = null) {

    fun getItemKey(): String?{
        return itemKey
    }
    fun getUserUID(): String? {
        return userUID
    }
    fun getItemName(): String? {
        return itemName
    }
    fun getItemDescription(): String? {
        return itemDescription
    }
    fun getItemImage(): String? {
        return itemImage
    }
    fun getItemPositionAddress(): String? {
        return itemPositionAddress
    }
    fun getItemType(): String? {
        return itemType
    }
}