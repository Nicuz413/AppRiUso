package com.example.appriuso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
    }

    fun takePicture(view: View) {}
    fun sendItem(view: View) {}
}