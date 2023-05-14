package com.example.queries.models

data class Item(
    val accepted_answer_id: Int,
    val answer_count: Int,
    val community_owned_date: Int,
    val content_license: String,
    val creation_date: Long,
    val is_answered: Boolean,
    val last_activity_date: Int,
    val last_edit_date: Int,
    val link: String,
    val locked_date: Int,
    val owner: Owner,
    val protected_date: Int,
    val question_id: Int,
    val score: Int,
    val tags: List<String>,
    val title: String,
    val view_count: Int
)