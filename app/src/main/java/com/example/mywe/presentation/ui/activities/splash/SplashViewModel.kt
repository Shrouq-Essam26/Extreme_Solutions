package com.example.mywe.presentation.ui.activities.splash



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.core.Resource
import com.example.mywe.data.model.Response
import com.example.mywe.domain.entities.AppConfigEntity
import com.example.mywe.domain.usecases.GetAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.reactivex.functions.Consumer


@HiltViewModel
class SplashViewModel @Inject constructor(private val getAppConfigUseCase: GetAppConfigUseCase) :
    ViewModel() {
    private val errorHandler: ErrorHandler = ErrorHandler()
    val config = MutableLiveData<State<AppConfigEntity>>()
    fun getConfig() {
        viewModelScope.launch(Dispatchers.IO) {
        config.postValue(State.Loading)
            try {
                getAppConfigUseCase.build().collect {
                    config.postValue(State.Success(data = it.data))
                }
            } catch (exception: Throwable) {
                config.postValue(
                    State.Error(exception = errorHandler.getError(exception))
                )
            }
        }
    }
}