package com.example.mywe.domain.usecases

import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.*
import com.example.mywe.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(private val repository: IRepository) {
    suspend fun build(addProducctToCartRequestEntity: AddProducctToCartRequestEntity): Flow<CartEntityItem> {
        return repository.AddProductTOCart(addProducctToCartRequestEntity)
    }

}