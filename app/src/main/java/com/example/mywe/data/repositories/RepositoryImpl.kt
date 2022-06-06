package com.example.mywe.data.repositories

import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.data.model.Response
import com.example.mywe.data.source.DataSource
import com.example.mywe.domain.entities.AppConfigEntity
import com.example.mywe.domain.repositories.IRepository
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource , private val errorHandler: ErrorHandler
): IRepository{

    private fun <T : Any> Single<T>.consumeSingle(onSuccess: ((obj: T) -> Unit)? = null): Single<State<T>> {
        return this.map {
            onSuccess?.invoke(it)
            State.Success(it) as State<T>
        }.onErrorReturn {
            State.Error(exception = errorHandler.getError(it))
        }
    }

    override suspend fun getAppConfig(): Flow<Response<AppConfigEntity>> {

        return dataSource.getAppConfig()
    }
}
