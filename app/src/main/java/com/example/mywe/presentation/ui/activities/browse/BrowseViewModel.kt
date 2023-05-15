package com.example.mywe.presentation.ui.activities.browse



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywe.ErrorHandler
import com.example.mywe.State
import com.example.mywe.domain.entities.CategoriesEntity
import com.example.mywe.domain.entities.ProductsEntity
import com.example.mywe.domain.usecases.GetCategoriesUseCase
import com.example.mywe.domain.usecases.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BrowseViewModel @Inject constructor(private val getCategoriesUseCase: GetCategoriesUseCase , private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase) :
    ViewModel() {
    private val errorHandler: ErrorHandler = ErrorHandler()
    val categories = MutableLiveData<State<CategoriesEntity>>()
    val productsByCategory = MutableLiveData<State<ProductsEntity>>()


    fun getProductsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
        categories.postValue(State.Loading)
            try {
                getProductsByCategoryUseCase.build(category).collect {
                    productsByCategory.postValue(State.Success(data = it))

                }
            } catch (exception: Throwable) {
                productsByCategory.postValue(
                    State.Error(exception = errorHandler.getError(exception))
                )
            }
        }
    }
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categories.postValue(State.Loading)
            try {
                getCategoriesUseCase.build().collect(){
                    categories.postValue(State.Success(data = it))
                }
            } catch (exception: Throwable) {
                categories.postValue(
                    State.Error(exception = errorHandler.getError(exception))
                )
            }
        }
    }
}