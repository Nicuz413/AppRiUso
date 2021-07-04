package com.example.appriuso

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MainItemsAdapter (private val list :ArrayList<Item>, val context: Context) : RecyclerView.Adapter<MainItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Item) {
            itemView.sentItemEmail.text = item.getEmail()
            itemView.sentItemName.text = item.getItemName()
            itemView.sentItemLocation.text = item.getItemPositionAddress()
            itemView.sentItemType.text = item.getItemType()
            itemView.sentItemDescription.text = item.getItemDescription()
            val encodeByte: ByteArray = Base64.decode(item.getItemImage(), Base64.DEFAULT)
            val imageStub = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            itemView.sentItemImage.setImageBitmap(imageStub)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
