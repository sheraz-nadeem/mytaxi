package com.mytaxi.sheraz.data.network.response

import com.google.gson.annotations.SerializedName
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry

data class TaxiListResponse(
    @SerializedName("poiList")
    val poiList: List<MyTaxiEntry>
)