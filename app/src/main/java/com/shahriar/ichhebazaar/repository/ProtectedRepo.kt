package com.shahriar.ichhebazaar.repository

import android.util.Log
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.category.CategoryResponse
import com.shahriar.ichhebazaar.data.product.ProductResponse
import com.shahriar.ichhebazaar.service.ProtectedService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProtectedRepo(private val api: ProtectedService) {

//  Products Response Repository
    suspend fun getProducts(): Flow<Resource<ProductResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.product() // Make the network request
            Log.d("Repository getProducts ", response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Repository getProducts error ", e.toString())
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

//  Category Response Repository
    suspend fun getCategories(): Flow<Resource<CategoryResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.getCategory() // Make the network request
//        Log.d("Repository get Categories ", response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Repository getProducts error ", e.toString())
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

}