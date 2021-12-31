package com.example.testapp.ui.map

import android.os.Bundle
import com.example.testapp.R
import com.example.testapp.common.BaseActivity
import com.example.testapp.databinding.ActivityMapBinding
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.content.Intent
import com.example.testapp.ui.details.PlaceMapActivityDetails
import com.example.testapp.ui.details.PlaceMapActivityDetails.Companion.PLACE_DETAILS_INFO_KEY


class MapActivity : BaseActivity(), OnMapReadyCallback {


    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var placesArrayList: ArrayList<PlaceDetailsInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            placesArrayList = it.getParcelableArrayList<PlaceDetailsInfo>(PLACES_DETAILS_ITEM_LIST_KEY) as ArrayList<PlaceDetailsInfo>
        }

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        placesArrayList.forEach {
            map.addMarker(
                MarkerOptions().position(LatLng(it.latitude, it.longitude)).title(it.name)
            )
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(placesArrayList.last().latitude, placesArrayList.last().longitude)))

        googleMap.setOnInfoWindowClickListener { marker ->
            placesArrayList.forEach {
                if (marker.title == it.name) {
                    val intent = Intent(this, PlaceMapActivityDetails::class.java)
                    intent.putExtra(PLACE_DETAILS_INFO_KEY, it)
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        const val PLACES_DETAILS_ITEM_LIST_KEY = "Places_details_item_list_key"
    }

}