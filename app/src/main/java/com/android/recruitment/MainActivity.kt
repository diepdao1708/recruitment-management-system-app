package com.android.recruitment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.recruitment.databinding.ActivityMainBinding
import com.android.recruitment.features.auth.AuthActivity
import com.android.recruitment.utils.AppEvent
import com.android.recruitment.utils.hideKeyboard
import com.android.recruitment.utils.setKeyboardVisibilityListener
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.bnvMain.apply {
            setupWithNavController(navController)
            setKeyboardVisibilityListener { visibility ->
                binding.bnvMain.isVisible = !visibility
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.resumeFragment, R.id.accountFragment -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }
    }

    private fun hideBottomNavigation() {
        binding.bnvMain.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        binding.bnvMain.visibility = View.VISIBLE
    }

    fun logout() {
        hideKeyboard()
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAppEvent(event: AppEvent) {
        if (event is AppEvent.LogOut) {
            viewModel.logout()
            logout()
        }
    }
}