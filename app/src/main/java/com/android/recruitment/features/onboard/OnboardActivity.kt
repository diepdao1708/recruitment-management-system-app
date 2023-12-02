package com.android.recruitment.features.onboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.recruitment.R
import com.android.recruitment.databinding.ActivityOnboardBinding
import com.android.recruitment.features.auth.AuthActivity

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listOnboard = listOf(
            OnboardData(
                R.drawable.intro_1,
                this.getString(R.string.intro_1),
                this.getString(R.string.intro_description_1)
            ),
            OnboardData(
                R.drawable.intro_2,
                this.getString(R.string.intro_2),
                this.getString(R.string.intro_description_2)
            ),
            OnboardData(
                R.drawable.intro_3,
                this.getString(R.string.intro_3),
                this.getString(R.string.intro_description_3)
            ),
            OnboardData(
                R.drawable.intro_4,
                this.getString(R.string.intro_4),
                this.getString(R.string.intro_description_4)
            ),
            OnboardData(
                R.drawable.intro_5,
                this.getString(R.string.intro_5),
                this.getString(R.string.intro_description_5)
            ),
            OnboardData(
                R.drawable.intro_6,
                this.getString(R.string.intro_6),
                this.getString(R.string.intro_description_6)
            ),
        )

        val onboardAdapter = OnboardAdapter(intros = listOnboard)
        binding.vpIntro.adapter = onboardAdapter
        binding.vpIntro.let { binding.onboardingDotsIndicator.attachTo(it) }
        binding.vpIntro.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvSkip.visibility = View.VISIBLE
                binding.tvNext.visibility = View.VISIBLE
                binding.tvSkip.text = this@OnboardActivity.getString(R.string.skip)
                binding.tvNext.text = this@OnboardActivity.getString(R.string.next)
            }
        })
        binding.tvNext.setOnClickListener {
            binding.vpIntro.currentItem.let {
                if (it == 5) {
                    navigateTo(AuthActivity::class.java)
                } else {
                    binding.vpIntro.currentItem = it + 1
                }
            }
        }
        binding.tvSkip.setOnClickListener {
            navigateTo(AuthActivity::class.java)
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