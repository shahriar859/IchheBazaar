package com.shahriar.ichhebazaar.ui.register

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.GuestApiClient
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.login.LoginResponse
import com.shahriar.ichhebazaar.data.registration.RegistrationInfoPayload
import com.shahriar.ichhebazaar.data.registration.RegistrationResponse
import com.shahriar.ichhebazaar.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel (application: Application) : AndroidViewModel(application){

    //API Response
    val registrationResponse = MutableStateFlow<RegistrationResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    private val authRepo = AuthRepo(GuestApiClient.api)


    fun registerProfile(name: String, phone: String, email: String, password: String, context: Context) {
        val newInstance = RegistrationInfoPayload(
            name, phone, email, password
        )

        viewModelScope.launch {
            authRepo.registerProfile(
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
                        val errorMessage = resource.message
                        Log.d("error", resource.message)
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}