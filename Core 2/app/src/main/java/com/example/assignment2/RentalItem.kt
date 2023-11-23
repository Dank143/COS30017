package com.example.assignment2

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

data class RentalItem(
    var name: String,
    var rating: Float,
    var multiChoiceAttr: String,
    var pricePerDay: Double,
    var topSpeed: String,
    var engine: String,
    var weight: String,
    var manufacturer: String,
    var imageResId: Int,      // Adding back the image resource ID property
    var borrowedDays: Int? = null,
    var dueDate: Date? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),          // Reading the imageResourceId from the parcel
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readSerializable() as? Date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(rating)
        parcel.writeString(multiChoiceAttr)
        parcel.writeDouble(pricePerDay)
        parcel.writeString(topSpeed)
        parcel.writeString(engine)
        parcel.writeString(weight)
        parcel.writeString(manufacturer)
        parcel.writeInt(imageResId)  // Writing imageResourceId to the parcel
        parcel.writeValue(borrowedDays)
        parcel.writeSerializable(dueDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RentalItem> {
        override fun createFromParcel(parcel: Parcel): RentalItem {
            return RentalItem(parcel)
        }

        override fun newArray(size: Int): Array<RentalItem?> {
            return arrayOfNulls(size)
        }
    }
}