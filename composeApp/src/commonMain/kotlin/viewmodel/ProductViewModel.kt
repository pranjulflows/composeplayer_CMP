package viewmodel

import Resources
import data.ProductsItem
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.ProductRepository

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<ProductsItem>>(emptyList())
    val products = _products.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            ProductRepository.getProducts().let { result ->
                when (result) {
                    is Resources.Failure -> println("Something went wrong ${result.exception}")
                    is Resources.Success -> _products.update { result.value.products }
                    Resources.Empty -> println("Loading Data....")
                }
            }
        }
    }
}
