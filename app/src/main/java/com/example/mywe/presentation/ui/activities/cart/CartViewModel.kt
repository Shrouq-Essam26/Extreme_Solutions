package com.example.mywe.presentation.ui.activities.cart



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.domain.entities.ProductEntityItem
import com.example.mywe.domain.usecases.GetProductById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(private val getProductById: GetProductById) :
    ViewModel() {
    private val errorHandler: ErrorHandler = ErrorHandler()
    val products = MutableLiveData<State<ProductEntityItem>>()
    fun getproducts( productId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            products.postValue(State.Loading)
            try {
                getProductById.build(productId).collect {
                    products.postValue(State.Success(data = it))
                }
            } catch (exception: Throwable) {
                products.postValue(
                    State.Error(exception = errorHandler.getError(exception))
                )
            }
       }
    }
}