package com.shahriar.ichhebazaar.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.ApiClient
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.login.LoginPayload
import com.shahriar.ichhebazaar.data.login.LoginResponse
import com.shahriar.ichhebazaar.data.registration.RegistrationInfoPayload
import com.shahriar.ichhebazaar.data.registration.RegistrationResponse
import com.shahriar.ichhebazaar.repository.ShopRepository
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


    private val repository = ShopRepository(ApiClient.api)


    init {

    }

    fun registerProfile(email: String, name: String, password: String, phone: String) {
        val newInstance = RegistrationInfoPayload(
            email, name, password, phone
        )

        viewModelScope.launch {
            repository.registerProfile(
                newInstance
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        registrationResponse.value = resource.data
                        Log.d("Viewmodel", resource.data.toString())
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                        Log.d("error", resource.message)
                    }
                }
            }
        }
    }


    fun userLogin(password: String, email: String) {
        val newInstance = LoginPayload(
            password, email
        )

        viewModelScope.launch {
            repository.userLogin(
                newInstance
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        loginResponse.value = resource.data
                        Log.d("Viewmodel", resource.data.toString())
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                        Log.d("error", resource.message)
                    }
                }
            }
        }
    }
}