package app.myquizapp.co.uk.data.remote

import com.squareup.moshi.Json

data class QuestionDto (

    @field:Json(name = "_id")
    val id: Int,
    @field:Json(name = "questionText")
    val questionText: String,
    val answers: List<AnswerDto>

)