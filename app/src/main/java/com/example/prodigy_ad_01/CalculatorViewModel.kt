package com.example.prodigy_ad_01

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prodigy_ad_01.MainActivity.Companion.TAG
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    private val _expression = MutableLiveData("")
    val expression: LiveData<String> = _expression


    private val _result = MutableLiveData("")
    val result: LiveData<String> = _result


    fun onButtonClick(value: String) {
        _expression.value = (_expression.value ?: "") + value
    }

    fun onAllClear() {
        _expression.value = ""
        _result.value = ""
    }

    fun onClear() {
        _expression.value?.let {
            if (it.isNotEmpty()) {
                _expression.value = it.substring(0, it.length - 1)
            }
        }
    }

    fun onEquals() {
        try {
            if (_expression.value != null && _result.value != null) {
                _result.value = ExpressionBuilder(_expression.value).build().evaluate().toString()
            }
        } catch (e1: NumberFormatException) {
            _result.value = e1.message.toString()
            Log.e(TAG, "onEquals: $e1")
        } catch (e: Exception) {
            _result.value = e.message.toString()
            Log.e(TAG, "onEquals: $e")
        }

    }

}