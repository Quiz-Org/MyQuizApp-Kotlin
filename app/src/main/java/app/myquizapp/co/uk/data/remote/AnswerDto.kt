package app.myquizapp.co.uk.data.remote

import com.squareup.moshi.Json

data class AnswerDto (

    @field:Json(name = "_id")
    val id: Int,
    val answerText: String,
    val correct: Boolean
)