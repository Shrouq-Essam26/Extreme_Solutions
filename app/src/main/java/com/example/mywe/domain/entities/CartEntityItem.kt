package com.example.mywe.domain.entities

data class CartEntityItem(
    val __v: Int,
    val date: String,
    val id: Int,
    val products: List<Product>,
    val userId: Int
):java.io.Serializable