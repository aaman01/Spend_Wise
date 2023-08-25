package com.example.lifesaver.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lifesaver.Database.Dao.TransactionDao
import com.example.lifesaver.Database.data.transaction

@Database(entities = arrayOf(transaction::class), version = 2)
 abstract class AppDatabase :RoomDatabase()
{
      abstract fun transactiondao(): TransactionDao

    companion object{
        //the momment any updation takes place in instance all threads are made avaialble of it
        @Volatile
        private var INSTANCE:AppDatabase?=null //private field to hold DB
        //public funtion to acc DB
        fun getDatabase(context: Context):AppDatabase{
            if (INSTANCE==null){
                //to prevent 2 or more threads to create same obj at same time
                //chane of multiple instance reation may occur
                //we use locking
                synchronized(this){
                    //obj of db created
                    INSTANCE= Room.databaseBuilder(context.applicationContext,AppDatabase::class.java, "ExpenseDB")
                        .fallbackToDestructiveMigration().build()
                }

            }
            return INSTANCE!!     // instance can canot be null that why !! is used
        }



    }

}