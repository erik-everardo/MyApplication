package com.example.myapplication.features.profile.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.core.activity_extras.Main.USER_ID
import com.example.myapplication.data.remote.dto.Post
import com.example.myapplication.data.remote.http_url.HttpUrlCon
import com.example.myapplication.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val viewmodel: ProfileViewModel by viewModels()
    lateinit var viewBinding: ActivityProfileBinding
    lateinit var refresher: SwipeRefreshLayout
    lateinit var ascSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        viewmodel.getProfile(intent.getIntExtra(USER_ID, -1))

        ascSwitch = viewBinding.ascending
        ascSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            refresher.isRefreshing = true
            viewmodel.getPostsJson(olderFirst = isChecked) {
                refresher.isRefreshing = false
            }
        }

        // Swipe to refresh
        refresher = viewBinding.refresh
        refresher.setOnRefreshListener {
            viewmodel.getPostsJson(olderFirst =  ascSwitch.isChecked) {
                refresher.isRefreshing = false
            }
        }
        refresher.isRefreshing = true

        viewmodel.getPostsJson(olderFirst = ascSwitch.isChecked) { refresher.isRefreshing = false }



        lifecycleScope.launch {
            Log.println(Log.ERROR, "Llega", "Llega a launch")
            viewmodel.profileState.collect {
                if (it != null) {
                    title = it.name
                    viewBinding.description.text = it.descriptionText
                    Picasso
                        .get()
                        .load("https://isolaatti.azurewebsites.net/api/Fetch/ProfileImages/${it.profileImageId}.png")
                        .resize(400, 400)
                        .into(viewBinding.imageView)
                }
            }
        }


        lifecycleScope.launch {
            val adapter = PostListAdapter()
            val recyclerView: RecyclerView = viewBinding.recyclerViewPosts
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)

            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.posts2.collect {
                    if(it != null){
                        adapter.submitList(it.data)
                        recyclerView.scrollTo(0,0)
                    }
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater

        inflater.inflate(R.menu.profile_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh -> {
                refresher.isRefreshing = true
                viewmodel.getProfile(intent.getIntExtra(USER_ID, -1))
                viewmodel.getPostsJson(olderFirst = ascSwitch.isChecked) { refresher.isRefreshing = false }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}