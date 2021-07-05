package com.example.appriuso

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
             updateUI(currentUser)}
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            val toMain = Intent(this, MainActivity::class.java)
            startActivity(toMain)
            finish()
        }
        else{
            val toMain = Intent(this, LoginActivity::class.java)
            startActivity(toMain)
            finish()
        }
    }

    fun register(view: View) {
        if(email.text.toString() != "" && password.text.toString() != "") {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
        else{
            if(email.text.toString() == "" )
                email.error = getString(R.string.invalid_message)
            if(password.text.toString() == "" )
                password.error = getString(R.string.invalid_message)
        }
    }

    fun login(view: View) {
        if(email.text.toString() != "" && password.text.toString() != "") {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext, "Autenticazione confermata.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(user)
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Autenticazione fallita.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
        else{
           if(email.text.toString() == "" )
               email.error = getString(R.string.invalid_message)
            if(password.text.toString() == "" )
                password.error = getString(R.string.invalid_message)
        }
    }
}