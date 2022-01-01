package com.example.testapp.common.util

import android.util.Log
import com.example.testapp.BuildConfig
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import java.text.DecimalFormat


fun calculationByDistance(StartP: LatLng, EndP: LatLng): Double {
    val Radius = 6371 // radius of earth in Km
    val lat1 = StartP.latitude
    val lat2 = EndP.latitude
    val lon1 = StartP.longitude
    val lon2 = EndP.longitude
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
            + (Math.cos(Math.toRadians(lat1))
            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
            * Math.sin(dLon / 2)))
    val c = 2 * Math.asin(Math.sqrt(a))
    val valueResult = Radius * c
    val km = valueResult / 1
    val newFormat = DecimalFormat("####")
    val kmInDec: Int = Integer.valueOf(newFormat.format(km))
    val meter = valueResult % 1000
    val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
    Log.i(
        "Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec
    )
    return Radius * c
}

fun getDistanceBetweenTwoPoints(firstPoint: LatLng, secondPoint: LatLng): Double {
    val distanceInKm = SphericalUtil.computeDistanceBetween(firstPoint, secondPoint) / 1000
    return String.format("%.1f", distanceInKm).toDouble()
}

fun getDistanceToSeattle(latitude: Double, longitude: Double): String {
    val centerOfSeattle = LatLng(BuildConfig.SEATTLE_LATTITUDE.toDouble(), BuildConfig.SEATTLE_LONGITUDE.toDouble())
    val selectedPoint = LatLng(latitude, longitude)
    return "To Seattle center is ${getDistanceBetweenTwoPoints(selectedPoint, centerOfSeattle)} km"
}