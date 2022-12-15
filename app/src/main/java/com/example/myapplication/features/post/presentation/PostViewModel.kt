package com.example.myapplication.features.post.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.WebApi
import com.example.myapplication.data.remote.dto.PostDataDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val api: WebApi) : ViewModel() {
    private val _post = MutableStateFlow<PostDataDto?>(null)
    val post = _post.asStateFlow()

    fun getPost(postId: Int) {
        viewModelScope.launch {
            val data = api.getPostById(postId, "63989419420d870926adcddb.5665744b-23d5-4da5-9b07-8de97c953fb6.CamrGCh1nyfQcHMUYyEeaByaKNkh4wo/oTBBxj6wZAPoyjrIHusPAhENaJv7Df5HqPzR8RQGWuQSNKIMS0yQ+Q==")
            _post.emit(data)
        }
    }
    
    
}