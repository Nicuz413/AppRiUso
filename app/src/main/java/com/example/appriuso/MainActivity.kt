package com.example.appriuso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sent.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var itemList: ArrayList<Item>
    private var stringSearched: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        database = Firebase.database("https://appriuso-747cc-default-rtdb.europe-west1.firebasedatabase.app/").getReference("items")
        itemList = ArrayList()
        createList()
        mainSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                itemList.clear()
                createList()
            }

        }
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                itemList.clear()
                stringSearched = newText
                createList()
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_new -> {val toInsertActivity = Intent(this, InsertActivity::class.java)
                startActivity(toInsertActivity)
                return true
            }
            R.id.action_settings ->{
                val sentItems = Intent(this,SentActivity::class.java)
                startActivity(sentItems)
                return true }
            R.id.action_logout ->{ auth.signOut()
                val returnToLogin = Intent(this, LoginActivity::class.java)
                startActivity(returnToLogin)
                return true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createList() {
        val myUserId = auth.uid

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (itemSnapshot in dataSnapshot.children) {
                        val item = itemSnapshot.getValue(Item::class.java)
                        if (item != null) {
                            if (item.getUserUID() != myUserId)
                                if(item.getItemType() == mainSpinner.selectedItem.toString())
                                        if(stringSearched.isNullOrBlank())
                                            itemList.add(item)
                                        else if(stringSearched?.let { item.getItemName().toString().contains(it, true)} == true)
                                            itemList.add(item)
                        }
                    }
                    val adapterStub = MainItemsAdapter(itemList, this@MainActivity)
                    mainItemList.adapter = adapterStub
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("KotlinQueryError", "loadPost:onCancelled", error.toException())
            }
        })
    }
}