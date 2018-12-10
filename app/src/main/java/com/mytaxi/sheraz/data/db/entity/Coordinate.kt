package com.mytaxi.sheraz.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo

data class Coordinate(
    @ColumnInfo(name = "coordinate_lat")
    val latitude: Double,
    @ColumnInfo(name = "coordinate_lng")
    val longitude: Double
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readDouble(),
        source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(latitude)
        writeDouble(longitude)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Coordinate> = object : Parcelable.Creator<Coordinate> {
            override fun createFromParcel(source: Parcel): Coordinate = Coordinate(source)
            override fun newArray(size: Int): Array<Coordinate?> = arrayOfNulls(size)
        }
    }
}