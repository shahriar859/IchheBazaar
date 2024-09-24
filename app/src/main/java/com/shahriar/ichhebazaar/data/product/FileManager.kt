package com.shahriar.ichhebazaar.data.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileManager(
    val created_at: String?,
    val id: Int?,
    val origin_id: Int?,
    val origin_type: String?,
    val updated_at: String?,
    val url: String?
): Parcelable