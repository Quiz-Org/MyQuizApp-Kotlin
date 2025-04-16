package app.myquizapp.co.uk.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuizApi {

    @GET("quiz/all")
    suspend fun getQuizzes(): Call<List<QuizDto>>

    @GET("quiz/{quizId}")
    suspend fun getQuestions(@Path("quizId") quizId: Int): Call<List<QuestionListDto>>

}