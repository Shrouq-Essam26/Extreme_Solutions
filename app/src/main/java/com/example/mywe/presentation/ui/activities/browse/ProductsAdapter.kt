package com.example.mywe.presentation.ui.activities.browse


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mywe.R
import com.example.mywe.domain.entities.ProductEntityItem


class ProductsAdapter(private val mList: List<ProductEntityItem>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val postData = mList[position]
        if (postData.image != null) {
            var requestOptions = RequestOptions()
            val path = postData.image

            requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
            Glide.with(context)
                .load(path)
                .apply(requestOptions)
                .skipMemoryCache(true)//for caching the image url in case phone is offline
                .into(holder.productImage)
            holder.productName.setText(postData.title)
            holder.productPrice.setText(postData.price.toString())
            holder.itemView.setOnClickListener {
                onClickListener.onClick(productEntityItem = postData)
            }
        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.with(context).clear(holder.productImage)
            // remove the placeholder (optional); read comments below
            holder.productImage.setImageDrawable(null)
            holder.productName.setText(postData.title)
            holder.productPrice.setText(postData.price.toString())

            holder.itemView.setOnClickListener {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(productEntityItem = postData)
                }
            }

        }


    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View ) : RecyclerView.ViewHolder(ItemView) {
        val productImage: ImageView = itemView.findViewById(R.id.iv_productImage)
        val productName: TextView = itemView.findViewById(R.id.tv_titleValue)
        val productPrice: TextView = itemView.findViewById(R.id.tv_priceValue)


    }
    class OnClickListener(val clickListener: (productEntityItem: ProductEntityItem) -> Unit) {
        fun onClick(productEntityItem: ProductEntityItem) = clickListener(productEntityItem)
    }


}