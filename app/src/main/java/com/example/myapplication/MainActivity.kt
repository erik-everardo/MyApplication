package com.example.myapplication

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.activity_extras.Main.USER_ID
import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.data.remote.dto.AuthVerificationDto
import com.example.myapplication.data.remote.http_url.HttpUrlCon
import com.example.myapplication.features.login.domain.repository.LogInRepository
import com.example.myapplication.features.login.presentation.LogInActivity
import com.example.myapplication.features.profile.domain.repository.ProfileRepository
import com.example.myapplication.features.profile.presentation.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val logInRepository: LogInRepository, private val userProfileRepository: ProfileRepository) : ViewModel() {
    private val _authState = MutableStateFlow(AuthVerificationDto(isValid = false, userId = 0))
    val isValid = _authState.asStateFlow()

    fun verifySession() {
        viewModelScope.launch {
            logInRepository.getCurrentAuthData().collect {authDataRetrieveResult ->
                if(authDataRetrieveResult is DataRetrieveResult.Success) {
                    authDataRetrieveResult.data?.let { authData ->
                        logInRepository.validateAuthData(authData.token).collect {authVerificationRetrieveResult ->
                            authVerificationRetrieveResult.data?.let { _authState.emit(it) }
                        }
                    }
                }
            }
        }
    }

    fun getStoredSession() {
        viewModelScope.launch {
            logInRepository.getCurrentAuthData().collect {dataRetrieveResult ->
                dataRetrieveResult.data.let {
                    userProfileRepository.getCurrentStoredUser().collect {userProfile ->
                        if (userProfile != null) {
                            _authState.emit(AuthVerificationDto(isValid = true, userId = userProfile.id))
                        }
                    }
                }
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val isConnected = cm.activeNetworkInfo?.isConnectedOrConnecting() == true

        lifecycleScope.launch {
            viewModel.isValid.collect {
                if(it.isValid) {
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
                        putExtra(USER_ID, it.userId)
                    })
                }
            }
        }

        if(!isConnected){

            val noInternetDialog = AlertDialog.Builder(this)
                .setTitle(R.string.no_internet)

            noInternetDialog.apply {
                setPositiveButton(R.string.continue_with_no_connection){ dialogInterface: DialogInterface, i: Int ->

                    viewModel.getStoredSession()
                }
                setNegativeButton(R.string.exit) { dialogInterface: DialogInterface, i: Int ->

                }
                setNeutralButton(R.string.retry) { dialogInterface: DialogInterface, i: Int ->
                    viewModel.verifySession()
                }
            }
            noInternetDialog.show()
        } else {
            viewModel.verifySession()
        }

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.signInBtn.setOnClickListener {
            onSignInClick()
        }

    }

    private fun onSignInClick(){
        startActivity(Intent(this, LogInActivity::class.java))
    }
}