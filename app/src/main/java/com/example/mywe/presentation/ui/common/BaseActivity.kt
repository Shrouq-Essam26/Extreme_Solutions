package com.example.mywe.presentation.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.mywe.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = getViewBinding()
        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        binding.root.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener{onBackPressed()}
        initDialog()
    }


    abstract fun getViewBinding(): B

    //region toolbar//
    @SuppressLint("ResourceAsColor")
    fun setupToolbar(titleResId: Int, isLight: Boolean = false, showUp: Boolean = true, show: Boolean = true) {
        setupToolbar(getString(titleResId), isLight, showUp, show)
    }

    @SuppressLint("ResourceAsColor")
    fun setupToolbar(title: String, isLight: Boolean = false, showUp: Boolean = true, show: Boolean = true) {
        when (show) {
            true -> {
                supportActionBar?.show()
                supportActionBar?.title = title
                supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
//                if (isLight) {
//                }
            }
            false -> supportActionBar?.hide()
        }
    }
    //endregion//

    //region Alert//
    private fun initDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage("message")
        builder.setCancelable(false)
        dialog = builder.create()
    }
//endregion//
    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.ContextWrapper.wrap(newBase, Locale(LocaleHelper.LANGUAGE))
        super.attachBaseContext(context)
    }
}