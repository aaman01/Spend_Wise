package com.example.lifesaver.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifesaver.Database.data.transaction
import com.example.lifesaver.Repository.Repository



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {


        fun addtransaction(transactionz: transaction) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.add(transactionz)
            }
        }

        fun deleteall() {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteall()
            }
        }

        fun delete(transactionzz: transaction) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.delete(transactionzz)
            }
        }

        fun update(transactionzz: transaction) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.update(transactionzz)
            }
        }


    }


