package app.myquizapp.co.uk.data.repository

import app.myquizapp.co.uk.data.remote.ScoreApi
import app.myquizapp.co.uk.data.remote.ScoreDto
import app.myquizapp.co.uk.domain.repository.ScoreRepository
import app.myquizapp.co.uk.domain.util.Resource
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(
    private val api: ScoreApi
): ScoreRepository {

    override suspend fun getScores(token: String): Resource<List<ScoreDto>> {
        return try {
            Resource.Success(
                data = api.getScores("Bearer $token")
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message?: "an unknown error has occurred")
        }

    }

    override suspend fun postScore(token: String, quizId: Int, score: Int, total: Int): Resource<Unit> {

        return try {
            Resource.Success(
                data = api.postScore("Bearer $token", ScoreDto(quizId, score, total))
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message?: "an unknown error has occurred")
        }

    }


}