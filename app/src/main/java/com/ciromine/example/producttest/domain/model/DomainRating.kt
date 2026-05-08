package com.ciromine.example.producttest.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DomainRating(
    val rate: Double,
    val count: Int
): Parcelable