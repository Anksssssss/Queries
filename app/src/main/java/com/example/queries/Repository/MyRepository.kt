package com.example.queries.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.queries.api.RetrofitInstance
import com.example.queries.data.QuestionItem
import com.example.queries.data.QuestionsDao
import com.example.queries.models.Item
import com.example.queries.models.QuestionsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRepository ( private val questionDao: QuestionsDao) {

    suspend fun fetchDataAndCache() {
        RetrofitInstance.api.getTrendingQuestions().enqueue(object : Callback<QuestionsList> {
            override fun onResponse(call: Call<QuestionsList>, response: Response<QuestionsList>) {
                if(response.body()!=null){
                    CoroutineScope(IO).launch {
                        cacheData(response.body()!!.items)
                    }

                }
                else return
            }

            override fun onFailure(call: Call<QuestionsList>, t: Throwable) {
                Log.d("MainViewModel",t.message.toString())
            }
        })
    }

    fun getCachedData(): List<QuestionItem>{
        return questionDao.getAllQuestions()
    }

    private suspend fun cacheData(items: List<Item>) {

        questionDao.deleteAll()
        var questionItem=QuestionItem()

        var count = 1

        for (i in items) {

            questionItem?.profile_image = i.owner.profile_image
            questionItem?.title = i.title
            questionItem?.viewCount = i.view_count
            questionItem?.answerCount = i.answer_count
            questionItem?.displayName = i.owner.display_name
            questionItem?.reputation = i.owner.reputation
            questionItem?.creationDate = i.creation_date
            questionItem?.link = i.link

            var tag = ""
            var c = 1
            for (t in i.tags) {
                if (c > 3) {
                    break
                }
                tag += t + ", "
                c++
            }
            questionItem?.tags = tag

            if (questionItem != null) {
                questionDao.insertQuestion(questionItem)
            }
            count++
            if(count>20){
                break
            }
        }
    }
}