package app.myquizapp.co.uk.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ScoreApi {

    @GET("/score")
    suspend fun getScores(@Header("Authorization") token: String): List<ScoreDto>

    @POST("/score")
    suspend fun postScore(@Header("Authorization") token: String, @Body scoreDto: ScoreDto)


}