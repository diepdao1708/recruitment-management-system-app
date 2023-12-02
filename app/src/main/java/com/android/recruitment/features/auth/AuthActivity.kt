package com.android.recruitment.features.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import com.android.recruitment.MainActivity
import com.android.recruitment.databinding.ActivityAuthBinding
import com.android.recruitment.utils.LoadingUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    private val googleSignInActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.handleLoginResult(it.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.googleLoginBtn.setOnClickListener {
            viewModel.loginWithGoogle()
        }

        viewModel.message.observe(this) {
            Toast.makeText(this@AuthActivity, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isLoading.distinctUntilChanged().observe(this) { isLoading ->
            if (isLoading) LoadingUtils.showLoading(this) else LoadingUtils.hideLoading()
        }
        viewModel.event.observe(this) {
            when (it) {
                AuthViewModel.Event.NavigateToHome -> {
                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                else -> {}
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.signInIntent.collectLatest {
                if (it != null) googleSignInActivityResultLauncher.launch(it)
            }
        }
    }
}