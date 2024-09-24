package com.shahriar.ichhebazaar.ui.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.ProtectedApiClient
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.product.ProductResponse
import com.shahriar.ichhebazaar.repository.ProtectedRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val productResponseFlow = MutableStateFlow<ProductResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    //DI
    private val protectedRepo = ProtectedRepo(ProtectedApiClient.api)
    init {
        getProducts()
    }


    fun getProducts() {
        viewModelScope.launch {
            protectedRepo.getProducts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        productResponseFlow.value = resource.data // Set news data
                        Log.d("Viewmodel", resource.data.toString())
                    }

                    is Resource.Error -> {
                        isLoading.value = false // Stop loading
                        errorMessage.value = resource.message // Set error message
                    }
                }
            }
        }

    }

}



