package com.example.lifesaver.Database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lifesaver.Database.data.transaction

@Dao
interface TransactionDao {

    @Query("SELECT * from transaction_table")
     fun getall():LiveData<List<transaction>>

    @Insert
    suspend fun insterall(vararg trans: transaction)

    @Delete
    suspend fun delete(transaction: transaction)

    @Update
    suspend fun update( vararg transaction: transaction)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAll()
}