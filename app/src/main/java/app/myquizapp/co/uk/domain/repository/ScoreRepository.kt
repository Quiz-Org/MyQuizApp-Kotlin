package app.myquizapp.co.uk.domain.repository

import app.myquizapp.co.uk.data.remote.ScoreDto
import app.myquizapp.co.uk.domain.util.Resource

interface ScoreRepository {

    suspend fun getScores(token: String): Resource<List<ScoreDto>>
    suspend fun postScore(token: String, quizId: Int, score: Int, total: Int): Resource<Unit>

}