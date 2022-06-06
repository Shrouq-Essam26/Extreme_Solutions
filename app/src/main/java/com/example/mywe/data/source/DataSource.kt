package com.example.mywe.data.source

import com.example.mywe.ErrorHandler
import com.example.mywe.data.api.Api
import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.AppConfigEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataSource @Inject constructor(private val api: Api, private val prefsHelper: PreferencesHelper, private val errorHandler: ErrorHandler) {
    suspend fun getAppConfig(): Flow<Response<AppConfigEntity>> {
        return flow { emit(api.getAppConfig())}
    }
}