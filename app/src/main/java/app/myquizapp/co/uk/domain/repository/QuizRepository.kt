package app.myquizapp.co.uk.domain.repository

import app.myquizapp.co.uk.domain.quiz.Question
import app.myquizapp.co.uk.domain.quiz.Quiz
import app.myquizapp.co.uk.domain.util.Resource

interface QuizRepository {

    suspend fun getAllQuizzes(): Resource<List<Quiz>>

    suspend fun getQuestions(quizId: Int): Resource<List<Question>>

}