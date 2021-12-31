package com.example.testapp.ui.details

import android.os.Bundle
import android.widget.Toast
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.common.BaseActivity
import com.example.testapp.common.util.getDistanceBetweenTwoPoints
import com.example.testapp.databinding.ActivityPlaceMapDetailsBinding
import com.example.testapp.ui.details.adapter.PlaceDetailsRecyclerViewAdapter
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import java.lang.reflect.Field


class PlaceMapActivityDetails : BaseActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityPlaceMapDetailsBinding
    lateinit var placeDetailsInfo: PlaceDetailsInfo
    private lateinit var adapter: PlaceDetailsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            placeDetailsInfo = it.getParcelable<PlaceDetailsInfo>(PLACE_DETAILS_INFO_KEY) as PlaceDetailsInfo
        }

        binding = ActivityPlaceMapDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (!::adapter.isInitialized) {
            adapter = PlaceDetailsRecyclerViewAdapter()
        }

        binding.detailsRecyclerView.adapter = adapter
        (binding.detailsRecyclerView.adapter as PlaceDetailsRecyclerViewAdapter).replaceAll(getInfoList(placeDetailsInfo))

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val selectedPoint = LatLng(placeDetailsInfo.latitude, placeDetailsInfo.longitude)
        val centerOfSeattle = LatLng(BuildConfig.SEATTLE_LATTITUDE.toDouble(), BuildConfig.SEATTLE_LONGITUDE.toDouble())
        mMap.addMarker(MarkerOptions().position(selectedPoint).anchor(0.5f, 0.5f).title(placeDetailsInfo.name))
        mMap.addMarker(MarkerOptions().position(centerOfSeattle).anchor(0.5f, 0.5f).title("Center of Seattle"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPoint))

        val distanceTitle = "To Seattle center is ${getDistanceBetweenTwoPoints(selectedPoint, centerOfSeattle)} km"
        showDistanceBetweenPoints(distanceTitle)

    }

    private fun showDistanceBetweenPoints(distance: String) {
        Toast.makeText(this, distance, Toast.LENGTH_LONG).show()
    }

    private fun getInfoList(placeDetailsInfo: PlaceDetailsInfo): ArrayList<Pair<String, String?>> {
        val infoList: ArrayList<Pair<String, String?>> = ArrayList()
        infoList.add(Pair("Name", placeDetailsInfo.name))
        infoList.add(Pair("Category", placeDetailsInfo.category))
        infoList.add(Pair("Distance", placeDetailsInfo.distance))
        infoList.add(Pair("Address", placeDetailsInfo.address))
        infoList.add(Pair("Country", placeDetailsInfo.country))
        infoList.add(Pair("Postcode", placeDetailsInfo.postcode))
        infoList.add(Pair("Region", placeDetailsInfo.region))
        return infoList
    }


    companion object {
        public final val PLACE_DETAILS_INFO_KEY = "Place_details_info_key"

    }

}