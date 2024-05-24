package network

import data.Products
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<Products>
}

const val baseUrl = "https://dummyjson.com/"
