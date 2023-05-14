package com.example.queries.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Questions")
data class QuestionItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var profile_image: String? = null,
    var title: String? = null,
    var viewCount: Int? = null,
    var answerCount: Int? = null,
    var displayName: String? = null,
    var reputation: Int? = null,
    var creationDate: Int? = null,
    var tags: String? = null,
    var link: String? = null,

    )