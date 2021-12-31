package com.example.testapp.ui.details.models

import android.os.Parcel
import android.os.Parcelable

data class PlaceDetailsInfo(
    val name: String?,
    val distance: String?,
    val category: String?,
    val fsq_id: String?,
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val country: String?,
    val postcode: String?,
    val region: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(name)
        parcel.writeString(distance)
        parcel.writeString(category)
        parcel.writeString(fsq_id)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(address)
        parcel.writeString(country)
        parcel.writeString(postcode)
        parcel.writeString(region)
    }

    companion object CREATOR : Parcelable.Creator<PlaceDetailsInfo> {
        override fun createFromParcel(parcel: Parcel): PlaceDetailsInfo {
            return PlaceDetailsInfo(parcel)
        }

        override fun newArray(size: Int): Array<PlaceDetailsInfo?> {
            return arrayOfNulls(size)
        }
    }

}
