package com.example.mywe.presentation.ui.activities.intro



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.domain.entities.AppConfigEntity
import com.example.mywe.domain.usecases.GetAppConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IntroViewModel @Inject constructor(private val getAppConfigUseCase: GetAppConfigUseCase) :
    ViewModel() {
    private val errorHandler: ErrorHandler = ErrorHandler()


}