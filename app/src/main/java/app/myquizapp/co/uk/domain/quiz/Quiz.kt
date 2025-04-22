package app.myquizapp.co.uk.domain.quiz

import androidx.compose.ui.graphics.Color

data class Quiz (

    val id: Int,
    val name: String,
    val desc: String,
    val category: QuizCategoryEnum,

    val colour: Color,

)