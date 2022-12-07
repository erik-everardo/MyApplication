package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.features.login.presentation.LogInActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.sign_in_btn).setOnClickListener {
            onSignInClick()
        }
    }

    private fun onSignInClick(){
        startActivity(Intent(this, LogInActivity::class.java))
    }
}