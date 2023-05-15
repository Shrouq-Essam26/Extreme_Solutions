package com.example.mywe.data.source

import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.data.api.Api
import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.*
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class DataSource @Inject constructor(private val api: Api, private val prefsHelper: PreferencesHelper, private val errorHandler: ErrorHandler) {
    suspend fun getAppConfig(): Flow<Response<AppConfigEntity>> {
        return flow { emit(api.getAppConfig())}
    }
    suspend fun getCategories(): Flow<CategoriesEntity> {
        return flow { emit(api.getCategories())}
    }

    suspend fun getProductsByCategoryes(category : String): Flow<ProductsEntity> {
        return flow { emit(api.getProductsByCategory(category))}
    }

    fun AddProductToCart(addProducctToCartRequestEntity: AddProducctToCartRequestEntity): Flow<CartEntityItem> {
        return flow { emit(api.putProductToCart(addProducctToCartRequestEntity))}
    }
    fun getProductById(productId :Int) : Flow<ProductEntityItem>
    {return  flow { emit(api.getProductById(productId)) }
    }

}