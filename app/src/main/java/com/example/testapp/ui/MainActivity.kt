package com.example.testapp.ui

import android.content.Intent
import android.os.Bundle
import com.example.testapp.App
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.models.Result
import com.example.testapp.common.BaseActivity
import com.example.testapp.common.util.getDistanceBetweenTwoPoints
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.example.testapp.ui.di.DaggerMainComponent
import com.example.testapp.ui.di.MainModule
import com.example.testapp.ui.main.fragments.PlacesFragment
import com.example.testapp.ui.map.MapActivity
import com.example.testapp.ui.map.MapActivity.Companion.PLACES_DETAILS_ITEM_LIST_KEY
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity(), PlacesFragment.ReceivePlacesInterface {

    lateinit var placesArrayList: List<Result>

    val component by lazy {
        DaggerMainComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(MainModule())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PlacesFragment.newInstance())
                .commitNow()
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val placesDetailsInfoList: ArrayList<PlaceDetailsInfo> = ArrayList()
            placesArrayList.forEach {
                val centerOfSeattle = LatLng(BuildConfig.SEATTLE_LATTITUDE.toDouble(), BuildConfig.SEATTLE_LONGITUDE.toDouble())
                val selectedPoint = LatLng(it.geocodes.main.latitude, it.geocodes.main.longitude)
                val distanceTitle = "To Seattle center is ${getDistanceBetweenTwoPoints(selectedPoint, centerOfSeattle)} km"
                val category = if(it.categories.isEmpty()) {
                    "No category"
                } else {
                    it.categories[0].name
                }
                placesDetailsInfoList.add(
                    PlaceDetailsInfo(
                        it.name,
                        distanceTitle,
                        category,
                        it.fsq_id,
                        it.geocodes.main.latitude,
                        it.geocodes.main.longitude,
                        it.location.address,
                        it.location.country,
                        it.location.postcode,
                        it.location.region
                    )
                )
            }

            val intent = Intent(this, MapActivity::class.java)
            intent.putParcelableArrayListExtra(PLACES_DETAILS_ITEM_LIST_KEY, placesDetailsInfoList)
            startActivity(intent)
        }
    }

    override fun onPlacesReceived(places: List<Result>) {
        placesArrayList = places
    }
}