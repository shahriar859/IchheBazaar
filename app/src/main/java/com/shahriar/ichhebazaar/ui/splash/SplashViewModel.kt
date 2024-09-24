package com.shahriar.ichhebazaar.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shahriar.ichhebazaar.api.ProtectedApiClient
import com.shahriar.ichhebazaar.datasource.DataStoreKeys
import com.shahriar.ichhebazaar.datasource.DatastoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val tokenFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val dataStoreManager = DatastoreManager(application)

    init {
        // Launch coroutine to collect the token from DataStore
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.getString(DataStoreKeys.token, null)
                .collect { token ->
                    tokenFlow.value = token  // Update the MutableStateFlow with token
                    token?.let {
                        ProtectedApiClient.updateToken(it)
                    }
                }
        }
    }

}
