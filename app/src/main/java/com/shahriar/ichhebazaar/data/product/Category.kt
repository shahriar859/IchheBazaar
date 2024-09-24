package com.shahriar.ichhebazaar.data.product
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int?,
    val name: String?,
    val priority: String?,
    val status: Int?
): Parcelable