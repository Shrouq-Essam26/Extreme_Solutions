package com.example.mywe.domain.entities

data class AddProducctToCartRequestEntity(
    var date: String,
    var products: List<Product>,
    var userId: Int = 1
)