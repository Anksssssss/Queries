package com.example.queries.api

import com.example.queries.models.QuestionsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsApi {

        @GET("questions?order=desc&sort=hot&site=stackoverflow")
        fun getTrendingQuestions(): Call<QuestionsList>

        @GET("search?order=desc&sort=votes&site=stackoverflow")
        fun getSearchedQuestions(
                @Query("intitle") question : String
        ):Call<QuestionsList>

//                @GET("search?order=desc&sort=votes&site=stackoverflow")
//        fun getSearchedQuestionsByTags(
//                @Query("intitle") question : String,
//                @Query("tagged") tag
//        ):



}