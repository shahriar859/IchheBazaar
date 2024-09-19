package com.shahriar.ichhebazaar.api

import com.shahriar.ichhebazaar.service.ShopService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    val api: ShopService by lazy {
        Retrofit.Builder()
            .baseUrl("https://sample-ecom.parallaxlogic.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopService::class.java)
    }
}