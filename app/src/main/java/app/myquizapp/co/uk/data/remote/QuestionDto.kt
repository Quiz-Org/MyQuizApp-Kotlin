package app.myquizapp.co.uk.data.remote

import com.squareup.moshi.Json

data class QuestionDto (

    @field:Json(name = "_id")
    val id: Int,
    val quizId:Int,
    val questionText: String,
    val answers: List<AnswerDto>

)