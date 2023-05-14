package com.example.queries.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuestionItem::class] , version = 3)
abstract class QuestionDatabase:RoomDatabase() {

    abstract fun questionsDao():QuestionsDao

    companion object{
        @Volatile
        var INSTANCE :QuestionDatabase? = null

        @Synchronized
        fun getInstance(context :Context):QuestionDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    QuestionDatabase::class.java,
                    "questions_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as QuestionDatabase
        }
    }
}