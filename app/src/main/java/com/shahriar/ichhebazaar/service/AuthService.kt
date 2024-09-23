package com.shahriar.ichhebazaar.service

import com.shahriar.ichhebazaar.data.login.LoginPayload
import com.shahriar.ichhebazaar.data.login.LoginResponse
import com.shahriar.ichhebazaar.data.registration.RegistrationInfoPayload
import com.shahriar.ichhebazaar.data.registration.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("user/register")
    suspend fun registerProfile(
        @Body body: RegistrationInfoPayload
    ): RegistrationResponse

    @POST("user/login")
    suspend fun login(
        @Body body: LoginPayload
    ): LoginResponse

}