package com.example.lifesaver.Repository

import com.example.lifesaver.Database.AppDatabase
import com.example.lifesaver.Database.data.transaction

class Repository(
   private val database: AppDatabase
) {

   suspend fun add(transactionz: transaction){
     database.transactiondao().insterall(transactionz)
  }
}