package com.android.recruitment.features

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.recruitment.MainActivity
import com.android.recruitment.databinding.ActivitySplashBinding
import com.android.recruitment.features.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            viewModel.event.collectLatest {
                when (it) {
                    SplashViewModel.Event.NavigateToLogin -> navigateTo(AuthActivity::class.java)
                    SplashViewModel.Event.NavigateToHome -> navigateTo(MainActivity::class.java)
                }
            }
        }
    }

    private fun <T> navigateTo(cls: Class<T>) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, cls)
            startActivity(intent)
            finish()
        }, 1800)
    }
}