package com.example.mywe.presentation.ui.activities.productDetails
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mywe.R
import com.example.mywe.State
import com.example.mywe.databinding.ActivityProductDetailsBinding
import com.example.mywe.domain.entities.AddProducctToCartRequestEntity
import com.example.mywe.domain.entities.Product
import com.example.mywe.domain.entities.ProductEntityItem
import com.example.mywe.presentation.ui.activities.cart.CartActivity
import com.example.mywe.presentation.ui.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun getViewBinding() = ActivityProductDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.app_name)
        val productItem = intent.getSerializableExtra("product") as? ProductEntityItem
        binding.tvTitle.text = productItem?.title
        binding.tvPriceValue.text = productItem?.price.toString()
        binding.tvDescriptionValue.text = productItem?.description
        var requestOptions = RequestOptions()
        val path = productItem?.image
        requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
        Glide.with(this)
            .load(path)
            .apply(requestOptions)
            .skipMemoryCache(true)//for caching the image url in case phone is offline
            .into(binding.ivProductImage)
        binding.tvRatingValue.text = productItem?.rating?.rate.toString()
        binding.tvCountValue.text = productItem?.rating?.count.toString()
        binding.btAddToCart.setOnClickListener {
            var  addProducctToCartRequestEntity = AddProducctToCartRequestEntity(Calendar.DATE.toString() , userId = 3 , products = listOf(Product(productItem?.id, 1)) )

            viewModel.getCart(addProducctToCartRequestEntity = addProducctToCartRequestEntity)
//
        }

        //       viewModel.getConfig()
        viewModel.cart.observe(this) {
            when (it) {
                is State.Loading -> {
                    dialog.show()
                }
                is State.Success -> {
                    val intent = Intent(this, CartActivity::class.java)
                    intent.putExtra("cart", it.data)
                    startActivity(intent)
                }


                is State.Error -> {
                    dialog.setMessage(it.exception.toString())
                    dialog.show()
                }
            }
//            }

        }
    }}
