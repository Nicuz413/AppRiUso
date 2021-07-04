package com.example.appriuso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sent.*

class SentActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var itemList: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent)
        auth = Firebase.auth
        database = Firebase.database("https://appriuso-747cc-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items")
        itemList = ArrayList()
        basicQueryValueListener()
    }

    private fun basicQueryValueListener() {
        val myUserId = auth.uid

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val adapterStub = ItemsAdapter(itemList, this@SentActivity)
                    for (itemSnapshot in dataSnapshot.children) {
                        val item = itemSnapshot.getValue(Item::class.java)
                        if (item != null) {
                            if (item.getUserUID() == myUserId)
                                itemList.add(item)
                        }
                    }
                    sentItemsList.adapter = adapterStub
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("KotlinQueryError", "loadPost:onCancelled", error.toException())
            }
        })
    }
}