package com.example.queries.models

data class ItemX(
    val collectives: List<Collective>,
    val count: Int,
    val has_synonyms: Boolean,
    val is_moderator_only: Boolean,
    val is_required: Boolean,
    val name: String
)