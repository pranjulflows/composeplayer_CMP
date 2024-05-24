package repository

import Resources
import data.Products
import network.KtorfitClient

object ProductRepository {
    suspend fun getProducts(): Resources<Products> {
//            Either<Throwable, Products>

        val response = KtorfitClient.api.getProducts()
        if (response.isSuccessful) {
            return Resources.Success(response.body() as Products)
        } else {
            return Resources.Failure(response.errorBody().toString())
        }
    }
}
