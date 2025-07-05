package com.example.supermarketapp.dataentities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf


class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    // Product list
    val productList = mutableStateListOf<Product>()

    init {
        loadProducts()
    }

    // loads products from database
    fun loadProducts() {
        viewModelScope.launch {
            val products = repository.getAll()
            productList.clear()
            productList.addAll(products)
        }
    }

    // adds product and reload the database
    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.insert(product)
            loadProducts()
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            repository.deleteAllProducts()
            loadProducts() // Επαναφορτώνει την κενή λίστα
        }
    }

}
