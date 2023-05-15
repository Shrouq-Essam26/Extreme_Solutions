package com.example.mywe.data.api

import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
    @GET("api/v1/settings/config")
    @Headers("No-Authentication: true")
    suspend fun getAppConfig(): Response<AppConfigEntity>

    @GET("products/categories")
    suspend fun getCategories(): CategoriesEntity
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path ("category") category: String ): ProductsEntity

    @PUT("carts/7")
     suspend fun putProductToCart(@Body body :AddProducctToCartRequestEntity):CartEntityItem
    @GET("products/{productId}")
    suspend fun getProductById(@Path ("productId") productId : Int): ProductEntityItem
}