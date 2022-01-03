package com.example.testapp.common.util

import android.location.Location
import com.example.testapp.BuildConfig
import com.google.android.gms.maps.model.LatLng


fun getDistanceBetweenTwoPoints(firstPoint: LatLng, secondPoint: LatLng): Double {
    var distance = FloatArray(1)
    Location.distanceBetween(firstPoint.latitude, firstPoint.longitude, secondPoint.latitude, secondPoint.longitude, distance)
    val distanceInKm = distance[0] / 1000
    return String.format("%.1f", distanceInKm).toDouble()
}

fun getDistanceToSeattle(latitude: Double, longitude: Double): String {
    val centerOfSeattle = LatLng(BuildConfig.SEATTLE_LATTITUDE.toDouble(), BuildConfig.SEATTLE_LONGITUDE.toDouble())
    val selectedPoint = LatLng(latitude, longitude)
    return "To Seattle center is ${getDistanceBetweenTwoPoints(selectedPoint, centerOfSeattle)} km"
}