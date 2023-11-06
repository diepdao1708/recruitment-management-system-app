package com.android.recruitment.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.recruitment.MainActivity
import com.android.recruitment.databinding.ActivityAuthBinding
import com.android.recruitment.features.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            viewModel.login()
        }

        binding.tvRegister.setOnClickListener {
            viewModel.register()
        }

        viewModel.event.observe(this) {
            when (it) {
                AuthViewModel.Event.NavigateToHome -> {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                AuthViewModel.Event.NavigateToRegister -> {
                    val intent = Intent(this@AuthActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else -> {}
            }
        }
    }
}