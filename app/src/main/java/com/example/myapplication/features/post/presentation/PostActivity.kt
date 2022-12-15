package com.example.myapplication.features.post.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.core.activity_extras.Post.POST_ID
import com.example.myapplication.databinding.ActivityPostBinding
import com.example.myapplication.databinding.PostBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityPostBinding
    lateinit var postViewBinding: PostBinding
    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityPostBinding.inflate(layoutInflater)
        postViewBinding = PostBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)
        
        val postId = intent.getIntExtra(POST_ID, -1)
        viewBinding.linearLayout.addView(postViewBinding.root)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.post.collect {
                    if (it != null) {
                        postViewBinding.username.text = it.userName
                        val markwon = Markwon.create(postViewBinding.root.context)

                        markwon.setMarkdown(postViewBinding.content, it.post.textContent)

                    }
                }
            }
        }

        viewModel.getPost(postId)




        
    }
}