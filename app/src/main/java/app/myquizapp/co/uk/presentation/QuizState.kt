package app.myquizapp.co.uk.presentation

import app.myquizapp.co.uk.domain.quiz.Quiz

data class QuizState(
    val quizzes: List<Quiz>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
