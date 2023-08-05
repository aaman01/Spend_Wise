package com.example.lifesaver.Database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class transaction(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var label:String,
    var amount:Double,
    var description:String
){

}
