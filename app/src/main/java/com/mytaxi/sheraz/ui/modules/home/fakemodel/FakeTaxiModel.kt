package com.mytaxi.sheraz.ui.modules.home.fakemodel


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mytaxi.sheraz.data.db.entity.Coordinate

data class FakeTaxiModel(
    @SerializedName("taxiList")
    val taxiList: List<Taxi>
) {
    data class Taxi(
        @SerializedName("coordinate")
        val coordinate: Coordinate,
        @SerializedName("driverImage")
        var driverImage: Int,
        @SerializedName("driverName")
        var driverName: String,
        @SerializedName("driverRating")
        var driverRating: Int,
        @SerializedName("fleetType")
        val fleetType: String,
        @SerializedName("heading")
        val heading: Double,
        @SerializedName("id")
        val id: Int,
        @SerializedName("vehicleDistanceFromUser")
        var vehicleDistanceFromUser: String,
        @SerializedName("vehicleEstimatedTimeOfArrival")
        var vehicleEstimatedTimeOfArrival: String,
        @SerializedName("vehicleModel")
        var vehicleModel: String
    ) : Parcelable {


        constructor(source: Parcel) : this(
            source.readParcelable<Coordinate>(Coordinate::class.java.classLoader),
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readDouble(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeParcelable(coordinate, 0)
            writeInt(driverImage)
            writeString(driverName)
            writeInt(driverRating)
            writeString(fleetType)
            writeDouble(heading)
            writeInt(id)
            writeString(vehicleDistanceFromUser)
            writeString(vehicleEstimatedTimeOfArrival)
            writeString(vehicleModel)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<Taxi> = object : Parcelable.Creator<Taxi> {
                override fun createFromParcel(source: Parcel): Taxi = Taxi(source)
                override fun newArray(size: Int): Array<Taxi?> = arrayOfNulls(size)
            }
        }
    }
}