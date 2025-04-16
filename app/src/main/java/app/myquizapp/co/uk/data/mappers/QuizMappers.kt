package app.myquizapp.co.uk.data.mappers

import app.myquizapp.co.uk.data.remote.QuizDto
import app.myquizapp.co.uk.domain.quiz.Quiz


fun QuizDto.toQuiz(): Quiz {
    return Quiz(
        id = this.id,
        name = this.name,
        desc = this.desc
    )
}