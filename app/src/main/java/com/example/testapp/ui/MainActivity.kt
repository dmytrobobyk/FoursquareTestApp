package com.example.testapp.ui

import android.content.Intent
import android.os.Bundle
import com.example.testapp.App
import com.example.testapp.R
import com.example.testapp.common.BaseActivity
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.example.testapp.ui.di.DaggerMainComponent
import com.example.testapp.ui.di.MainModule
import com.example.testapp.ui.main.fragments.PlacesFragment
import com.example.testapp.ui.map.MapActivity
import com.example.testapp.ui.map.MapActivity.Companion.PLACES_DETAILS_ITEM_LIST_KEY
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity(), PlacesFragment.ReceivePlacesInterface {

    private var placesArrayList: ArrayList<PlaceDetailsInfo> = ArrayList()

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
            val intent = Intent(this, MapActivity::class.java)
            intent.putParcelableArrayListExtra(PLACES_DETAILS_ITEM_LIST_KEY, placesArrayList)
            startActivity(intent)
        }
    }

    override fun onPlacesReceived(places: List<PlaceDetailsInfo>) {
        placesArrayList.clear()
        placesArrayList.addAll(places)
    }
}