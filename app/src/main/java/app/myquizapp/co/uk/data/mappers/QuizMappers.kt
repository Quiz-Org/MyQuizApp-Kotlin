package app.myquizapp.co.uk.data.mappers

import androidx.compose.ui.graphics.Color
import app.myquizapp.co.uk.data.remote.QuizDto
import app.myquizapp.co.uk.domain.quiz.Quiz
import app.myquizapp.co.uk.domain.quiz.QuizCategoryEnum


fun QuizDto.toQuiz(): Quiz {
    return Quiz(
        id = this.id,
        name = this.name,
        desc = this.desc,
        category = this.category,
        colour = this.category.toColour()
    )
}

fun QuizCategoryEnum.toColour(): Color{

    return when(this) {
        QuizCategoryEnum.HISTORY -> Color.Blue
        QuizCategoryEnum.TECHNOLOGY -> Color.Magenta
        QuizCategoryEnum.GENERAL_KNOWLEDGE -> Color.Green
        QuizCategoryEnum.POP_CULTURE -> Color.Red
    }

}

//fun List<QuizDto>.toQuizList(): List<Quiz>{
//
//
//
//}