package com.example.lifesaver.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.lifesaver.Database.AppDatabase
import com.example.lifesaver.Database.data.transaction

class Repository(
   private val database: AppDatabase
) {

   suspend fun add(transactionz: transaction){
     database.transactiondao().insterall(transactionz)
  }
    suspend fun deleteall(){
        database.transactiondao().deleteAll()
    }
    suspend fun delete(t: transaction){
        database.transactiondao().delete(t)
    }

    suspend fun update(t: transaction){
        database.transactiondao().update(t)
    }

}