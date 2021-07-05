package com.example.appriuso

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null

    private lateinit var location : String
    private val defaultLocation = LatLng(45.829221758297436, 8.821894447020572)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.MainMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        location = intent.getStringExtra("address").toString()
    }


    override fun onMapReady(mapHandle: GoogleMap) {
        googleMap = mapHandle
        if (location == "null"){
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 5F))
            Toast.makeText(this, "Luogo del ritiro non inserito", Toast.LENGTH_SHORT).show()
            }
        if(location != "null") {
            val coder = Geocoder(this)
            val selectedLocation: LatLng?

            val address: List<Address> = coder.getFromLocationName(location, 5)

            val locations: Address = address[0]
            selectedLocation = LatLng(locations.latitude, locations.longitude)
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 15F))
            googleMap?.addMarker(
                MarkerOptions()
                    .position(selectedLocation)
                    .title("Luogo del ritiro")
            )
        }
    }
}