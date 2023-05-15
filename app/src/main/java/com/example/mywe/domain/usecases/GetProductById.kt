package com.example.mywe.domain.usecases

import com.example.mywe.domain.entities.ProductEntityItem
import com.example.mywe.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductById @Inject constructor(private val repository: IRepository) {
    suspend fun build(productId: Int): Flow<ProductEntityItem> {
        return repository.getProductById(productId = productId)   }

}