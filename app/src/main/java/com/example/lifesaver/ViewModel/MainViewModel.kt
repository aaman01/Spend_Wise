package com.example.lifesaver.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifesaver.Database.data.transaction
import com.example.lifesaver.Repository.Repository



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):ViewModel() {


//    fun getmoviee(page:String){
//        viewModelScope.launch(Dispatchers.IO) {
//         repository.getmovie(page)
//        }
//    }
//
//    val movie:LiveData<MovieList>
//        get() = repository.movies
//
//  fun deletemovie(id:String){
//      viewModelScope.launch(Dispatchers.IO) {
//          repository.deletemovie(id)
//      }
//  }
//
//    fun insertfav(id:String,value:Boolean){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insertfav(id,value)
//        }
//    }

    fun addtransaction(transactionz: transaction){
        viewModelScope.launch ( Dispatchers.IO){
            repository.add(transactionz)
        }
    }



}