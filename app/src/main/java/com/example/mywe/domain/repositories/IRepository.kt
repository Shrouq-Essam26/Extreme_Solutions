package com.example.mywe.domain.repositories

import com.example.mywe.State
import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.*
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getAppConfig(): Flow<Response<AppConfigEntity>>
    suspend fun getAppCategories(): Flow<CategoriesEntity>
    suspend fun getProductsByCategory(category: String): Flow<ProductsEntity>
     suspend fun AddProductTOCart(addProducctToCartRequestEntity: AddProducctToCartRequestEntity): Flow<CartEntityItem>
   suspend fun getProductById(productId :Int) : Flow<ProductEntityItem>
}