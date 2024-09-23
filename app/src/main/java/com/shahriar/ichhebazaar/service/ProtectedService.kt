package com.shahriar.ichhebazaar.service

import com.shahriar.ichhebazaar.data.product.ProductResponse
import retrofit2.http.GET

interface ProtectedService {

    @GET("user/product")
    suspend fun product(): ProductResponse

}