package com.example.mywe.domain.usecases

import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.AppConfigEntity
import com.example.mywe.domain.entities.CategoriesEntity
import com.example.mywe.domain.repositories.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: IRepository) {
    suspend fun build(): Flow<CategoriesEntity> {
        return repository.getAppCategories()    }

}