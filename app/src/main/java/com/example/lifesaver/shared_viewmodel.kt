package com.example.lifesaver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class shared_viewmodel:ViewModel() {
    private val _booleanLiveData = MutableLiveData<Boolean>()
    val booleanLiveData: LiveData<Boolean>
        get() = _booleanLiveData

    fun setBooleanValue(newValue: Boolean) {
        _booleanLiveData.value = newValue
    }
    var id:Int=-1
}