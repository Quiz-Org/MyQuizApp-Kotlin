package app.myquizapp.co.uk.data.remote

import app.myquizapp.co.uk.domain.quiz.QuizCategoryEnum
import com.squareup.moshi.Json

data class QuizDto(

    @field:Json(name = "_id")
    val id: Int,
    val name: String,
    @field:Json(name = "description")
    val desc: String,
    val category: QuizCategoryEnum

)
