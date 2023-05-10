package com.example.queries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queries.ViewModel.SearchViewModel
import com.example.queries.adapters.TagsAdapter
import com.example.queries.adapters.TrendingQuestionsAdapter
import com.example.queries.databinding.ActivitySearchBinding
import com.example.queries.models.Item
import com.example.queries.utils.Constants
import com.example.queries.utils.Constants.Companion.QUESTION
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchBinding
    lateinit var searchViewModel: SearchViewModel
    lateinit var trendingQuestionsAdapter: TrendingQuestionsAdapter
    lateinit var tagsAdapter : TagsAdapter
    lateinit var itemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        trendingQuestionsAdapter = TrendingQuestionsAdapter()
        tagsAdapter = TagsAdapter()

        prepareRecyclerView()

        prepareTagsRecyclerView()
        searchViewModel.getTags()
        searchViewModel.observeTagsLiveData().observe(this, Observer {
            tagsAdapter.differ.submitList(it)
        })

        var intent = intent
        var question = intent.getStringExtra(QUESTION)

        if (question != null) {
            searchViewModel.getsearchedQuestions(question)
        }



        searchViewModel.observeSearchedQuestionsLiveData().observe(this, Observer { items->
            itemList = items
            trendingQuestionsAdapter.differ.submitList(itemList)
        })

        onSearchItemClick()

//        binding.svSearchActivity.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterList(newText)
//                return true
//            }
//        })

        onTagsItemClick()

    }

    private fun onTagsItemClick() {
        tagsAdapter.onItemClick = {
            val tag = it.name
            val filteredList = ArrayList<Item>()
            for(i in itemList){
                for(j in i.tags){
                    if(j.toLowerCase().equals(tag.toLowerCase())){
                        filteredList.add(i)
                    }
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this,"No data Found",Toast.LENGTH_SHORT).show()
            }else{
                trendingQuestionsAdapter.differ.submitList(filteredList)
            }
        }
    }

    private fun filterList(query:String?){
        if(query!=null){
            val filteredList = ArrayList<Item>()

            for(i in itemList) {
                var t :String = ""
                for(j in i.tags){
                    t +=j +", "
                }
                    if (t.contains(query)) {
                        filteredList.add(i)
                    }


            }
            if(filteredList.isEmpty()){
                Toast.makeText(this,"No data Found",Toast.LENGTH_SHORT).show()
            }else{
                itemList = filteredList
            }
        }
    }

    private fun onSearchItemClick() {
        trendingQuestionsAdapter.onItemClick = {
            val intent = Intent(this,QuestionPage::class.java)
            intent.putExtra(Constants.LINK,it.link)
            startActivity(intent)
        }
    }

    private fun prepareTagsRecyclerView(){
        binding.rvTags.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = tagsAdapter
        }
    }

    private fun prepareRecyclerView() {
        binding.rvSearchFragment.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = trendingQuestionsAdapter
        }
    }

}