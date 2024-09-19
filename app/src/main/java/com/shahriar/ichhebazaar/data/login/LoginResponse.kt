package com.shahriar.ichhebazaar.data.login

data class LoginResponse(
    val message: String?,
    val token: String?,
    val user: User?
)