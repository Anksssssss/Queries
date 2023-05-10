package com.example.queries.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.queries.api.RetrofitInstance
import com.example.queries.models.Item
import com.example.queries.models.ItemX
import com.example.queries.models.QuestionsList
import com.example.queries.models.TagList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel :ViewModel() {

    private val searchedQuestionsLiveData = MutableLiveData<List<Item>>()
    private val tagsLiveData = MutableLiveData<List<ItemX>>()

    fun getsearchedQuestions(question:String){
        RetrofitInstance.api.getSearchedQuestions(question).enqueue(object :Callback<QuestionsList>{
            override fun onResponse(call: Call<QuestionsList>, response: Response<QuestionsList>) {
                if(response.body()!=null){
                    searchedQuestionsLiveData.postValue(response.body()!!.items)
                }
            }

            override fun onFailure(call: Call<QuestionsList>, t: Throwable) {
                Log.d("SearchViewModel",t.message.toString())
            }
        })
    }

    fun observeSearchedQuestionsLiveData():LiveData<List<Item>>{
        return searchedQuestionsLiveData
    }

    fun getTags(){
        RetrofitInstance.api.getTags().enqueue(object :Callback<TagList>{
            override fun onResponse(call: Call<TagList>, response: Response<TagList>) {
                if(response.body()!=null){
                    tagsLiveData.postValue(response.body()!!.items)
                }
            }

            override fun onFailure(call: Call<TagList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observeTagsLiveData():LiveData<List<ItemX>>{
        return tagsLiveData
    }
}