package com.example.myapplication.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.features.login.domain.repository.LogInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(val signInRepository: LogInRepository) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState(false, null))
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()


    fun signIn(email: String, password: String){
        viewModelScope.launch {
            signInRepository.authenticateUserWithCredential(email, password).collectLatest {
                // Aqui se recibe el auth, aunque ya se guardó
                _signInState.emit(when(it){
                    is DataRetrieveResult.Success -> SignInState(
                        validationError = false,
                        success = true
                    )
                    is DataRetrieveResult.Error -> SignInState(
                        validationError = false,
                        success = false
                    )
                    is DataRetrieveResult.Loading -> SignInState(
                        validationError = false,
                        success = null
                    )
                })
            }
        }
    }

}