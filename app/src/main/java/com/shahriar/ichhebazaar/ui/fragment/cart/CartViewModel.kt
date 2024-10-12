package com.shahriar.ichhebazaar.ui.fragment.cart

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.ProtectedApiClient
import com.shahriar.ichhebazaar.api.Resource
import com.shahriar.ichhebazaar.data.category.CategoryResponse
import com.shahriar.ichhebazaar.repository.ProtectedRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    val categoryResponseFlow = MutableStateFlow<CategoryResponse?>(null)
    val isLoading = MutableStateFlow(true)
    val errorMessage = MutableStateFlow<String?>(null)

    private val protectedRepo = ProtectedRepo(ProtectedApiClient.api)

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            protectedRepo.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true // Set loading state
                    }

                    is Resource.Success -> {
                        isLoading.value = false // Stop loading
                        categoryResponseFlow.value = resource.data // Set news data
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