package com.example.myapplication.features.login.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.databinding.ActivityLogInBinding
import com.example.myapplication.features.profile.presentation.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityLogInBinding
    val viewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewBinding.signInBtn.setOnClickListener {
            signIn()
        }

        lifecycleScope.launch() {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signInState.collect {
                    if(it.validationError) {
                        Toast.makeText(this@LogInActivity,"Error",Toast.LENGTH_SHORT).show()
                    }

                    if(it.success == true){
                        Toast.makeText(this@LogInActivity,"Welcome",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LogInActivity, ProfileActivity::class.java))
                    }
                }
            }
        }
    }

    private fun signIn() {
        val emailIsValid = viewBinding.emailTxtField.text.toString().isNotEmpty()
        val passwordIsValid = viewBinding.pwdTxtField.text.toString().isNotEmpty()

        viewBinding.email.isErrorEnabled = !emailIsValid
        viewBinding.password.isErrorEnabled = !passwordIsValid

        if(!emailIsValid || !passwordIsValid) {
            return
        }
        val email = viewBinding.emailTxtField.text.toString()
        val password = viewBinding.pwdTxtField.text.toString()

        viewModel.signIn(email, password)

    }


}