package com.example.queries.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(questionItems: QuestionItem)

    @Query("DELETE FROM Questions")
    fun deleteAll();

    @Query("SELECT * FROM Questions")
    fun getAllQuestions() : List<QuestionItem>
}