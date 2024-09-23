package com.shahriar.ichhebazaar.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.GuestApiClient
import com.shahriar.ichhebazaar.api.ProtectedApiClient
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.login.LoginPayload
import com.shahriar.ichhebazaar.data.login.LoginResponse
import com.shahriar.ichhebazaar.datasource.DataStoreKeys
import com.shahriar.ichhebazaar.datasource.DatastoreManager
import com.shahriar.ichhebazaar.repository.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    // Variables
    val emailStateFlow = MutableStateFlow<String>("")
    val passwordStateFlow = MutableStateFlow<String>("")

    //API Response
    val loginResponse = MutableStateFlow<LoginResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    //DI
    private val authRepo = AuthRepo(GuestApiClient.api)
    private val dataStoreManager = DatastoreManager(application)

    // Update email
    fun onEmailChanged(email: String) {
        emailStateFlow.value = email
    }

    // Update password
    fun onPasswordChanged(password: String) {
        passwordStateFlow.value = password
    }


    fun userLogin(context: Context) {
        val credentials = LoginPayload(
            user_id = emailStateFlow.value,
            password = passwordStateFlow.value
        )
        viewModelScope.launch {
            authRepo.userLogin(
                credentials
            ).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        loginResponse.value = resource.data // Set news data
                        Log.d("Viewmodel", resource.data.toString())
                        resource.data.token?.let {
                            dataStoreManager.saveString(DataStoreKeys.token, it)
                            // Inject token into the API client
                            ProtectedApiClient.updateToken(it)
                        }
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                        Toast.makeText(context, errorMessage.value, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

}