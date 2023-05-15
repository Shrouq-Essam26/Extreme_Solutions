package com.example.mywe.domain.entities

data class ProductEntityItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
):java.io.Serializable