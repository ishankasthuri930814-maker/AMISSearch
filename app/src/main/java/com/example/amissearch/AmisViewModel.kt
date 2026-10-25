package com.example.amissearch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed interface UiState {
    object Idle : UiState
    object Loading : UiState
    data class Success(val records: List<SuspectRecord>) : UiState
    data class Error(val message: String) : UiState
}

class AmisViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
    var searchType by mutableStateOf("nic") // "nic" or "name"
    var uiState: UiState by mutableStateOf(UiState.Idle)
        private set

    fun performSearch() {
        if (searchQuery.isBlank()) {
            uiState = UiState.Error("Krupaye NIC athava Hesarannu type maadi.")
            return
        }

        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val request = AmisRequest(query = searchQuery, type = searchType)
                val response = RetrofitClient.apiService.searchAmis(request)

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    val list = body.data ?: emptyList()
                    uiState = UiState.Success(list)
                } else {
                    uiState = UiState.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                uiState = UiState.Error("Network Error: ${e.localizedMessage}")
            }
        }
    }
}
