package com.mytaxi.sheraz.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "my_taxi")
data class MyTaxiEntry(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val rowId: Int,

    @ColumnInfo(name = "taxiId")
    @SerializedName("id")
    val taxiId: Int,

    @ColumnInfo(name = "fleetType")
    @NonNull
    val fleetType: String,

    @ColumnInfo(name = "heading")
    val heading: Double,

    @Embedded(prefix = "coordinate_")
    val coordinate: Coordinate
)