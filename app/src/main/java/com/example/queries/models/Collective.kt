package com.example.queries.models

data class Collective(
    val description: String,
    val external_links: List<ExternalLink>,
    val link: String,
    val name: String,
    val slug: String,
    val tags: List<String>
)