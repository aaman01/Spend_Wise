package com.example.lifesaver.Database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val label:String ,
    val amount:Double,
    val description:String
){

}
