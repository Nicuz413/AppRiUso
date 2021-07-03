package com.example.appriuso

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_insert.*


class InsertActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        auth = Firebase.auth
    }

    fun takePicture(view: View) {
        val items = arrayOf("Take Photo", "Choose from Library", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            when(items[item]){
                "Take Photo" -> cameraIntent()
                "Choose from Library" -> galleryIntent()
                "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_FROM_GALLERY = 2

    private fun cameraIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {

        }
    }

    private fun galleryIntent() {
        val takePhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_FROM_GALLERY)
        } catch (e: ActivityNotFoundException) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_IMAGE_CAPTURE -> {
                if(resultCode == RESULT_OK){
                    val extras : Bundle? = data?.extras
                    val bitmap : Bitmap? = extras?.get("data") as Bitmap?
                    imageView.setImageBitmap(bitmap)
                }
            }
            REQUEST_IMAGE_FROM_GALLERY -> {
                if(resultCode == RESULT_OK){
                    val pickedImage : Uri? = data?.data
                    imageView.setImageURI(pickedImage)
                }
            }
        }

    }

    fun sendItem(view: View) {
        //var newItem = Item(auth.uid, editName.text.toString(), description.text.toString(), imageView.id, Location(mapView.x)
    }
}