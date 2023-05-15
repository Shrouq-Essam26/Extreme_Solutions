package com.example.mywe.presentation.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.res.ResourcesCompat
import com.example.mywe.R
import com.example.mywe.data.model.SpinnerModel


import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SingleSelectionSpinner : AppCompatSpinner, DialogInterface.OnMultiChoiceClickListener {
    var listener: OnItemSelectedListener? = null
    private var _items: List<SpinnerModel> = listOf()
    private var simpleAdapter: ArrayAdapter<SpinnerModel> //TODO use Model
    private var selectedItemIndex = 0
    private var hint = ""
    private val selectedIndex: Int
        get() {
            for (index in _items.indices) {
                if (_items[index].isSelected) {
                    return index
                }
            }
            return -1
        }

    interface OnItemSelectedListener {
        fun selectedItem(selection: SpinnerModel)
    }

    constructor(context: Context) : super(context) { // TODO remove one

        simpleAdapter = ArrayAdapter(
            context,
            R.layout.simple_spinner_item
        )
        super.setAdapter(simpleAdapter) // TODO custom Adapter
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        simpleAdapter = ArrayAdapter(context, R.layout.simple_spinner_item)
        super.setAdapter(simpleAdapter)
    }

    fun setHint(h: String) {
        this.hint = h
        setSpinnerHint()
    }

    fun setOnItemsSelectedListener(listener: OnItemSelectedListener) {
        this.listener = listener
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if (!_items.isNullOrEmpty() && which < _items.size) {
            _items[which].isSelected = isChecked
            simpleAdapter.clear()
            selectedItemIndex = which
            if (selectedIndex != -1)
                simpleAdapter.add(_items[selectedIndex])
            else
                setSpinnerHint()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun performClick(): Boolean {
        if (_items.isNullOrEmpty()) return false
            MaterialAlertDialogBuilder(context , R.style.AlertDialogTheme
            )
            .setTitle(hint)
            .setBackground(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.spinner_border_white,
                    resources.newTheme()
                )
            )
            .setSingleChoiceItems(_items.map { it.name }.toTypedArray(), selectedIndex, this)
            .setOnDismissListener {
                if (selectedIndex != -1)
                    listener?.selectedItem(_items[selectedIndex])
            }
            .show()
        return true
    }

    fun setItems(items: List<SpinnerModel>) {

        _items = items
        simpleAdapter.clear()
        if (selectedIndex != -1)
            simpleAdapter.add(_items[selectedIndex])
        else
            setSpinnerHint()
        resetSelection()
    }

    fun setSelection(selection: SpinnerModel, cancel: Boolean = false) {
        var index = 0
        for (j in _items.indices) {
            if (_items[j] == selection) {
                _items[j].isSelected = true
                index = j
            }
        }
        simpleAdapter.clear()
        simpleAdapter.add(_items[index])
        if (!cancel)
            listener?.selectedItem(_items[index])
    }

    override fun setSelection(index: Int) {
        for (item in _items) {
            item.isSelected = false
        }
        if (index >= 0 && index < _items.size) {
            _items[index].isSelected = true
        } else {
            throw IllegalArgumentException(
                "Index " + index
                        + " is out of bounds."
            )
        }
        simpleAdapter.clear()
        simpleAdapter.add(_items[index])
    }

    fun setSelectedItem(id: Int, cancel: Boolean = false) {
        if (!_items.isNullOrEmpty() && id != -1 && _items.find { it.id == id } != null) {
            setSelection(_items.find { it.id == id } ?: _items[0], cancel)
            _items.find { it.id == id }?.isSelected = true
        } else {
            setSpinnerHint()
            resetSelection()
        }
    }

    fun resetSelection() {
        _items.forEach { it.isSelected = false }
    }

    fun setSpinnerHint() {
        simpleAdapter.clear()
        simpleAdapter.add(SpinnerModel(name = hint))
    }
}