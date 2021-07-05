package com.example.appriuso

import android.content.Intent
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

    private lateinit var sentItemList: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sent)
        auth = Firebase.auth
        database = Firebase.database("https://appriuso-747cc-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items")
        sentItemList = ArrayList()
        createList()
    }

    private fun createList() {
        val myUserId = auth.uid

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (itemSnapshot in dataSnapshot.children) {
                        val item = itemSnapshot.getValue(Item::class.java)
                        if (item != null) {
                            if (item.getUserUID() == myUserId)
                                sentItemList.add(item)
                        }
                    }
                    val adapterStub = SentItemsAdapter(sentItemList, this@SentActivity)
                    sentItemsList.adapter = adapterStub
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("KotlinQueryError", "loadPost:onCancelled", error.toException())
            }
        })
    }

    override fun onPause() {
        super.onPause()
        if(isFinishing) {
            val returnToMain = Intent(this, MainActivity::class.java)
            startActivity(returnToMain)
        }
    }
}