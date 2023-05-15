package com.example.mywe.presentation.ui.activities.productDetails



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.domain.entities.AddProducctToCartRequestEntity
import com.example.mywe.domain.entities.CartEntityItem
import com.example.mywe.domain.repositories.IRepository
import com.example.mywe.domain.usecases.AddProductToCartUseCase
import com.example.mywe.presentation.ui.common.BaseSchedulerProvider
import com.example.mywe.presentation.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.function.Consumer
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val addProductToCartUseCase: AddProductToCartUseCase) :
    ViewModel() {
    private val errorHandler: ErrorHandler = ErrorHandler()
    val cart = MutableLiveData<State<CartEntityItem>>()

    fun getCart(addProducctToCartRequestEntity: AddProducctToCartRequestEntity?) {
        viewModelScope.launch(Dispatchers.IO) {
        cart.postValue(State.Loading)
            try {
                addProductToCartUseCase.build(addProducctToCartRequestEntity!!).collect {
                    cart.postValue(State.Success(data = it))
                }
            } catch (exception: Throwable) {
                cart.postValue(
                    State.Error(exception = errorHandler.getError(exception))
                )
            }
        }
    }
}