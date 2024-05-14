package data

import kotlinx.serialization.Serializable

@Serializable
data class Products(
	val total: Int,
	val limit: Int,
	val skip: Int,
	val products: List<ProductsItem> = emptyList()
)

@Serializable
data class ProductsItem(
	val discountPercentage: Float? = null,
	val thumbnail: String? = null,
	val images: List<String>,
	val price: Double? = null,
	val rating: Double? = null,
	val description: String? = null,
	val id: Int? = null,
	val title: String,
	val stock: Int? = null,
	val category: String? = null,
	val brand: String? = null
)

