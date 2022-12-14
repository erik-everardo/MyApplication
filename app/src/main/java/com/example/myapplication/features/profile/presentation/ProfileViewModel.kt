package com.example.myapplication.features.profile.presentation

import android.util.Log
import android.util.Log.ERROR
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.util.DataRetrieveResult
import com.example.myapplication.data.remote.WebApi
import com.example.myapplication.data.remote.dto.FeedDto
import com.example.myapplication.data.remote.dto.Post
import com.example.myapplication.data.remote.dto.PostDataDto
import com.example.myapplication.data.remote.http_url.HttpUrlCon
import com.example.myapplication.features.login.domain.model.AuthData
import com.example.myapplication.features.login.domain.repository.LogInRepository
import com.example.myapplication.features.profile.domain.model.UserProfile
import com.example.myapplication.features.profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logInRepository: LogInRepository,
    private val profileRepository: ProfileRepository,
    private val xml: HttpUrlCon,
    private val api: WebApi
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthData?>(null)
    val uiState = _uiState.asStateFlow()

    private val _profileState = MutableStateFlow<UserProfile?>(null)
    val profileState = _profileState.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>?>(null)
    val posts = _posts.asStateFlow()

    private val _posts2 = MutableStateFlow<FeedDto?>(null)
    val posts2 = _posts2.asStateFlow()

    fun getAuthData(){
        viewModelScope.launch {
            logInRepository.getCurrentAuthData().collectLatest {
                if(it is DataRetrieveResult.Success) {
                    _uiState.emit(it.data)
                }
            }
        }
    }

    fun getProfile(userId: Int) {
        viewModelScope.launch {
            profileRepository.getProfile(userId).collect {
                when(it) {
                    !is DataRetrieveResult.Success -> it.message?.let { message ->
                        Log.println(ERROR, "Error on request", message)
                    }
                    else -> _profileState.emit(it.data)
                }

            }
        }
    }

    fun getPostsXml(onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO){
            xml.getPage().collect {
                _posts.emit(it)
                onComplete()
            }

        }
    }

    fun getPostsJson(olderFirst: Boolean, onComplete: () -> Unit) {
        viewModelScope.launch {
            _posts2.emit(api.getPostsByUserId(
                userId = 1,
                token = "63989419420d870926adcddb.5665744b-23d5-4da5-9b07-8de97c953fb6.CamrGCh1nyfQcHMUYyEeaByaKNkh4wo/oTBBxj6wZAPoyjrIHusPAhENaJv7Df5HqPzR8RQGWuQSNKIMS0yQ+Q==",
                length = 30,
                olderFirst = olderFirst
                ))
            onComplete()
        }
    }

//    fun getPosts(){
//        viewModelScope.launch {
//            HttpUrlCon.getPage().collect {
//
//            }
//        }
//    }
}