package com.example.myapplication.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.features.login.domain.model.AuthData
import com.example.myapplication.features.login.domain.repository.LogInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val logInRepository: LogInRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthData?>(null)
    val uiState = _uiState.asStateFlow()

    fun getAuthData(){
        viewModelScope.launch {
            logInRepository.getCurrentAuthData().collectLatest {
                if(it is DataRetrieveResult.Success) {
                    _uiState.emit(it.data)
                }
            }
        }
    }
}