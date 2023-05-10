package com.example.queries.models

import com.example.queries.models.Item

data class QuestionsList(
    val has_more: Boolean,
    val items: List<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)