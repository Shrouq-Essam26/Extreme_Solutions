package com.example.mywe.presentation.ui.activities.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mywe.R
import com.example.mywe.State
import com.example.mywe.databinding.ActivityIntroBinding

import com.example.mywe.presentation.ui.activities.browse.BrowseActivity
import com.example.mywe.presentation.ui.activities.cart.CartActivity
import com.example.mywe.presentation.ui.activities.productDetails.ProductDetailsActivity
import com.example.mywe.presentation.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>() {
    private val viewModel: IntroViewModel by viewModels()

    override fun getViewBinding() = ActivityIntroBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.app_name)
        binding.btBrowse.setOnClickListener {
            val intent = Intent(this, BrowseActivity::class.java)
            startActivity(intent)
        }
//        viewModel.getConfig()
//        viewModel.config.observe(this){
//                when (it) {
//                 is   State.Loading -> {
//                        dialog.show()
//                    }
//                is    State.Success -> {
//                    if (it.data.android_version.isNotEmpty())
//                        dialog.dismiss()
//                        binding.tvHello.text = it.data?.android_version
//                    }
//                    is  State.Error -> {
//                        dialog.setMessage(it.exception.toString())
//                        dialog.show()
//                    }
//                }
//            }

        }
    }
