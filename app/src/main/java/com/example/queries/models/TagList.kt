package com.example.queries.models

data class TagList(
    val has_more: Boolean,
    val items: List<ItemX>,
    val quota_max: Int,
    val quota_remaining: Int
)