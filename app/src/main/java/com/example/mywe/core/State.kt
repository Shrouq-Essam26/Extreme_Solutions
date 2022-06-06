package com.example.mywe


sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val exception: ErrorEntity) : State<Nothing>()
    object Loading : State<Nothing>()
}