package app.myquizapp.co.uk.data.repository

import app.myquizapp.co.uk.data.mappers.toQuestion
import app.myquizapp.co.uk.data.mappers.toQuiz
import app.myquizapp.co.uk.data.remote.QuizApi
import app.myquizapp.co.uk.domain.quiz.Question
import app.myquizapp.co.uk.domain.quiz.Quiz
import app.myquizapp.co.uk.domain.repository.QuizRepository
import app.myquizapp.co.uk.domain.util.Resource
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val api: QuizApi
): QuizRepository  {

    override suspend fun getAllQuizzes(): Resource<List<Quiz>> {
        return try {
            Resource.Success(
                data = api.getQuizzes().map { it.toQuiz() }
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message?: "an unknown error has occurred")
        }
    }

    override suspend fun getQuestions(quizId: Int): Resource<List<Question>> {
        return try {
            Resource.Success(
                data = api.getQuestions(quizId).map { it.toQuestion() }
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message?: "an unknown error has occurred")
        }
    }

}
