package com.shahriar.ichhebazaar.repository

import android.util.Log
import com.shahriar.ichhebazaar.service.ShopService
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.login.LoginPayload
import com.shahriar.ichhebazaar.data.login.LoginResponse
import com.shahriar.ichhebazaar.data.registration.RegistrationInfoPayload
import com.shahriar.ichhebazaar.data.registration.RegistrationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ShopRepository(private val api: ShopService) {

    suspend fun registerProfile(
        profile: RegistrationInfoPayload
    ): Flow<Resource<RegistrationResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.registerProfile(profile) // Make the network request
            Log.d("Repository", response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

    suspend fun userLogin(
        profile: LoginPayload
    ): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading) // Emit loading state
            val response = api.userLogin(profile) // Make the network request
            Log.d("Repository", response.toString())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)

}