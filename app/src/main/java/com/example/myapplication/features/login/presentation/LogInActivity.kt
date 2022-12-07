package com.example.myapplication.features.login.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLogInBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import com.google.android.material.textfield.TextInputLayout

import kotlinx.coroutines.launch

class LogInActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val viewmodel: LogInViewModel by viewModels { LogInViewModel.Factory }
        val dataBinding: ActivityLogInBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        dataBinding.viewmodel = viewmodel

        val emailText = findViewById<TextInputLayout>(R.id.email)


        lifecycleScope.launch {
            viewmodel.validation.collectLatest {
                emailText.isErrorEnabled = !it.emailIsValid()
            }
        }
    }
}