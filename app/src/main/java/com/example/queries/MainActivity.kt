package com.example.queries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.queries.ViewModel.MainViewModel
import com.example.queries.adapters.CustomAdapter
import com.example.queries.adapters.TrendingQuestionsAdapter
import com.example.queries.databinding.ActivityMainBinding
import com.example.queries.utils.Constants.Companion.LINK
import com.example.queries.utils.Constants.Companion.QUESTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var trendingQuestionsAdapter: TrendingQuestionsAdapter
    lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        trendingQuestionsAdapter = TrendingQuestionsAdapter()
        val itemList = mutableListOf<Any>()
        customAdapter = CustomAdapter(itemList)


        //prepareRecyclerView()
        prepareRecyclerViewOfCustomAdapter()

        GlobalScope.launch (Dispatchers.IO){
            mainViewModel.getTrendingQuestions()
        }


        mainViewModel.observeQuestionsLiveData().observe(this, Observer { items->


            for ((index, item) in items.withIndex()) {
                itemList.add(item)

                if ((index + 1) % 5 == 0) {
                    // Add an ad item to the itemList
                    itemList.add("Ad") // Replace "Ad" with your ad item or model
                }
            }
            customAdapter.notifyDataSetChanged()

           // trendingQuestionsAdapter.differ.submitList(items)
        })

        onItemClick()

        binding.searchButton.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            intent.putExtra(QUESTION,binding.etMainActivity.text.toString())
            startActivity(intent)
        }

    }

    private fun prepareRecyclerViewOfCustomAdapter() {
        binding.rvTrendingQuestions.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = customAdapter
        }
    }

        private fun onItemClick() {
            customAdapter.onItemClick = {
                val intent = Intent(this, QuestionPage::class.java)
                intent.putExtra(LINK, it.link)
                startActivity(intent)
            }
        }

        private fun prepareRecyclerView() {
            binding.rvTrendingQuestions.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = trendingQuestionsAdapter
            }
        }

    }