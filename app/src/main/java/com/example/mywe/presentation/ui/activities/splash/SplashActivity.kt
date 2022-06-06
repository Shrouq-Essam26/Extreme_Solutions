package com.example.mywe.presentation.ui.activities.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.example.mywe.R
import com.example.mywe.State
import com.example.mywe.core.Status
import com.example.mywe.databinding.ActivitySplashBinding
import com.example.mywe.presentation.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.app_name)
        viewModel.getConfig()
        viewModel.config.observe(this){
                when (it) {
                 is   State.Loading -> {
                        dialog.show()
                    }
                is    State.Success -> {
                    if (it.data.android_version.isNotEmpty())
                        dialog.dismiss()
                        binding.tvHello.text = it.data?.android_version
                    }
                    is  State.Error -> {
                        dialog.setMessage(it.exception.toString())
                        dialog.show()
                    }
                }
            }

        }
    }
