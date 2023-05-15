package com.example.mywe.presentation.ui.activities.cart


import android.annotation.SuppressLint
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
import com.example.mywe.domain.entities.CartEntity

import com.example.mywe.domain.entities.cartElement


class CartAdapter(private val mList: List<cartElement>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_layout, parent, false)
        return ViewHolder(view)

    }

    // binds the list items to a view
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val postData = mList[position]
            holder.productName.setText(postData.title)
            holder.productPrice.setText(postData.price.toString())
            holder.productQuantity.setText(postData.quantity.toString())
            holder.productTotal.setText((postData.quantity*postData.price).toString())

            holder.itemView.setOnClickListener {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick( postData)
                }
            }

        }





    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View ) : RecyclerView.ViewHolder(ItemView) {
        val productName: TextView = itemView.findViewById(R.id.tv_productTitleValue)
        val productPrice: TextView = itemView.findViewById(R.id.tv_productPriceValue)
        val productQuantity: TextView = itemView.findViewById(R.id.tv_productQuantityValue)
        val productTotal: TextView = itemView.findViewById(R.id.tv_totalValue)

    }
    class OnClickListener(val clickListener: (cartElement :cartElement) -> Unit) {
        fun onClick(cartElement: cartElement) = clickListener(cartElement)
    }


}