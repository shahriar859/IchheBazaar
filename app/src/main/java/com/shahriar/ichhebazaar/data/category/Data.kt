package com.shahriar.ichhebazaar.data.category

data class Data(
    val id: Int?,
    val name: String?,
    val priority: String?,
    val status: Int?,
    val sub_categories: List<SubCategory?>?
)