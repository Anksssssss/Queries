package com.example.queries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebViewClient
import com.example.queries.databinding.ActivityMainBinding
import com.example.queries.databinding.ActivityQuestionPageBinding
import com.example.queries.utils.Constants.Companion.LINK

class QuestionPage : AppCompatActivity() {

    lateinit var binding : ActivityQuestionPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent
        var questionLink = intent.getStringExtra(LINK)

        binding.webView.apply {
            webViewClient = WebViewClient()
            if (questionLink != null) {
                loadUrl(questionLink)
            }
        }
    }
}