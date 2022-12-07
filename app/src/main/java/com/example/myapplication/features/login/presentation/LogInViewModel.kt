package com.example.myapplication.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.features.login.domain.model.UserCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ValidationData(private val credential: UserCredential){
    // Improve this
    fun emailIsValid() = credential.email.isNotBlank()
    fun passwordIsValid() = credential.password.isNotBlank()
}

class LogInViewModel() : ViewModel() {

    private val _userCredential: UserCredential = UserCredential("","")

    private val _validation: MutableStateFlow<ValidationData> = MutableStateFlow(ValidationData(_userCredential))
    val validation: StateFlow<ValidationData> = _validation


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LogInViewModel() as T
            }

        }
    }
}