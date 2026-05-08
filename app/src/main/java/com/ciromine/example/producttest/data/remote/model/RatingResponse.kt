package com.ciromine.example.producttest.data.remote.model

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("rate")
    val rate: Double,

    @SerializedName("count")
    val count: Int
)