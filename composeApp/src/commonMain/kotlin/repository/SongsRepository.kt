package repository

import Resources
import data.SongData
import network.KtorfitClient

object SongsRepository {
    suspend fun getSong(): Resources<SongData> {
        val result = KtorfitClient.api.getSong(id = "yDeAS8Eh")
        if (result.isSuccessful) {
            return Resources.Success(result.body() as SongData)
        } else {
            return Resources.Failure(result.errorBody() as String)
        }
    }
}
