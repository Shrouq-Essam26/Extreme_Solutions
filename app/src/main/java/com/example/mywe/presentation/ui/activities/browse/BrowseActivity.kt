package com.example.mywe.presentation.ui.activities.browse

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywe.R
import com.example.mywe.State
import com.example.mywe.data.model.SpinnerModel
import com.example.mywe.databinding.ActivityBrowseBinding

import com.example.mywe.domain.entities.CategoriesEntity
import com.example.mywe.domain.entities.ProductEntityItem
import com.example.mywe.presentation.ui.activities.productDetails.ProductDetailsActivity
import com.example.mywe.presentation.ui.common.BaseActivity
import com.example.mywe.presentation.ui.common.SingleSelectionSpinner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrowseActivity : BaseActivity<ActivityBrowseBinding>() {
    private val viewModel: BrowseViewModel by viewModels()
    val products = ArrayList<ProductEntityItem>()
    var matchedProducts = ArrayList<ProductEntityItem>()


    override fun getViewBinding() = ActivityBrowseBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.app_name)

        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        viewModel.getCategories()
        viewModel.categories.observe(this) {
            when (it) {
                is State.Loading -> {
                    dialog.show()
                    dialog.setMessage("Loading")
                }
                is State.Success -> {
                    if (it.data.isNotEmpty())
                        dialog.dismiss()
                    bindCategoriesToAdapter(it.data)
                }
                is State.Error -> {
                    dialog.setMessage(it.exception.toString())
                    dialog.show()
                }
            }
        }

        viewModel.productsByCategory.observe(this) {
            when (it) {
                is State.Loading -> {
                    dialog.show()
                }
                is State.Success -> {
                    if (it.data.isNotEmpty())
                        dialog.dismiss()
                    for (i in 0 until it.data.size) {
                        products.add(
                         ProductEntityItem(it.data[i].category , it.data[i].description , it.data[i].id, it.data[i].image, it.data[i].price , it.data[i].rating , it.data[i].title)
                        )
                    }
                    // This will pass the ArrayList to our Adapter
                    val adapter = ProductsAdapter(products , ProductsAdapter.OnClickListener{
                        var productEntityItem: ProductEntityItem = it
                        val intent = Intent(this, ProductDetailsActivity::class.java)
                        intent.putExtra("product" , productEntityItem)
                        startActivity(intent)
                    })

                    // Setting the Adapter with the recyclerview
                    binding.rvProducts.adapter = adapter
                    performSearch()
                }
                is State.Error -> {
                    dialog.setMessage(it.exception.toString())
                    dialog.show()
                }
            }
        }



    }
    fun bindCategoriesToAdapter(categories: List<String>) {
        binding.spCategories.setHint("Categories")
        binding.spCategories.setItems(categories.map {
            SpinnerModel(id = it.indexOf(it), name = it)
        })
        binding.spCategories.setOnItemsSelectedListener( object :
            SingleSelectionSpinner.OnItemSelectedListener {
            override fun selectedItem(selection: SpinnerModel) {

                viewModel.getProductsByCategory(selection.name)
            }
        }
        )

    }
    private fun performSearch() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }
     fun search(text: String?) {
        matchedProducts = arrayListOf()

        text?.let {
            products.forEach {product ->
                if (product.title.contains(text, true) ||
                    product.description.contains(text, true)
                ) {
                    matchedProducts.add(product)
                }
            }
            updateRecyclerView()
            if (matchedProducts.isEmpty()) {
                Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }
    private fun updateRecyclerView() {
        binding.rvProducts.apply {
            val adapter = ProductsAdapter(matchedProducts, ProductsAdapter.OnClickListener {
                var productEntityItem: ProductEntityItem = it
                val intent = Intent(this.context, ProductDetailsActivity::class.java)
                intent.putExtra("product" , productEntityItem)
                startActivity(intent)
            })
            adapter?.notifyDataSetChanged()
            binding.rvProducts.adapter = adapter
        }
    }}