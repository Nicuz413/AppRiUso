package com.example.appriuso

import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.list_item.view.*

class SentItemsAdapter(private val list :ArrayList<Item>, val context: Context) : RecyclerView.Adapter<SentItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var database: DatabaseReference

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

        fun removedItem(item: Item, list: ArrayList<Item>){
            database = Firebase.database("https://appriuso-747cc-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items")
            item.getItemKey()?.let { database.child(it).ref.removeValue() }
            list.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])

        holder.itemView.setOnLongClickListener {
            val items = arrayOf("Disattiva inserzione", "Cancel")
            val builder: AlertDialog.Builder = AlertDialog.Builder(it.context)
            builder.setItems(items) { dialog, item ->
                when(items[item]){
                    "Disattiva inserzione" -> {
                        holder.removedItem(list[position], list)
                        notifyDataSetChanged()
                    }
                    "Cancel" -> dialog.dismiss()
                }
            }
            builder.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
