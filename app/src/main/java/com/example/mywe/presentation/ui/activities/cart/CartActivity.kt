package com.example.mywe.presentation.ui.activities.cart

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywe.R
import com.example.mywe.State
import com.example.mywe.databinding.ActivityCartBinding

import com.example.mywe.domain.entities.CartEntity
import com.example.mywe.domain.entities.CartEntityItem
import com.example.mywe.domain.entities.ProductEntityItem
import com.example.mywe.domain.entities.cartElement

import com.example.mywe.presentation.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {
    private val viewModel: CartViewModel by viewModels()

    override fun getViewBinding() = ActivityCartBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.app_name)

        val cartInfo = ArrayList<cartElement>()
        binding.rvProductsInCart.layoutManager = LinearLayoutManager(this)
        val cart= intent.getSerializableExtra("cart") as? CartEntityItem
        Log.e("CART" , cart.toString())
        for (i in 0 until cart?.products?.size!!)
        {
//            productIds.add(cart.products[i].productId!!)
//            productQuantities.add(cart.products[i].quantity!!)
//
            viewModel.getproducts(cart?.products[i]?.productId!!)
            viewModel.products.observe(this){
                when (it) {
                    is   State.Loading -> {
                        dialog.show()
                    }
                    is    State.Success -> {

                            dialog.dismiss()
                        cartInfo.add( cartElement(it.data.title , it.data.price , cart.products[i].quantity!!))
                        val adapter = CartAdapter(cartInfo , CartAdapter.OnClickListener{
                            var cartElement:cartElement =it
                        })

                        binding.rvProductsInCart.adapter = adapter

                    }
                    is  State.Error -> {
                        dialog.setMessage(it.exception.toString())
                        dialog.show()
                    }
                }
            }
        }
        Log.e("CartSize" , cartInfo.size.toString())


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
