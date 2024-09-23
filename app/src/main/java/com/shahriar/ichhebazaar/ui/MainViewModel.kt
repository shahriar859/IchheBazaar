package com.shahriar.ichhebazaar.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.login.LoginPayload
import com.shahriar.ichhebazaar.data.login.LoginResponse
import com.shahriar.ichhebazaar.data.registration.RegistrationInfoPayload
import com.shahriar.ichhebazaar.data.registration.RegistrationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    // Saving only NewsResponse instead of the Resource wrapper
    val registrationResponse = MutableStateFlow<RegistrationResponse?>(null)
    val loginResponse = MutableStateFlow<LoginResponse?>(null)

    // Separate variables to handle loading and error states
    val isLoading = MutableStateFlow(true)

    // Separate variables to handle loading and error states
    val errorMessage = MutableStateFlow<String?>(null)


//    private val repository = ShopRepository(ApiClient.api)


    init {

    }



}