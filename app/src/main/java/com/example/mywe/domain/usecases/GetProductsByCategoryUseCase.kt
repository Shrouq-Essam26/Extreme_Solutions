package com.example.mywe.domain.usecases

import com.example.mywe.domain.entities.ProductsEntity
import com.example.mywe.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(private val repository: IRepository) {
    suspend fun build(category: String): Flow<ProductsEntity> {
        return repository.getProductsByCategory(category = category)    }


}