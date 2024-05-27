package network

import data.Products
import data.SongData
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface ApiService {
    @GET("songs/{songId}")
    suspend fun getSong(
        @Path("songId") id: String,
    ): Response<SongData>
}

const val baseUrl = "https://saavn.dev/api/"
