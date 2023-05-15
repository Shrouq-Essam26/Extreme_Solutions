package com.example.mywe.data.model

data class SpinnerModel ( val id: Int = -1, val name: String, var isSelected: Boolean = false){
    override fun toString(): String {
        return name
    }
}