package app.myquizapp.co.uk.domain.repository

import app.myquizapp.co.uk.domain.quiz.Quiz
import app.myquizapp.co.uk.domain.util.Resource

interface QuizRepository {

    suspend fun getAllQuizes(): Resource<Quiz>

}