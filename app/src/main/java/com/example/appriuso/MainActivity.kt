package com.example.appriuso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
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
}