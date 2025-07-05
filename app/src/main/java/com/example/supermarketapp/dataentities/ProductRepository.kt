package com.example.supermarketapp.dataentities
import androidx.annotation.WorkerThread


class ProductRepository(private val productDao: ProductDao) {

    // new product
    @WorkerThread
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    // all products
    @WorkerThread
    suspend fun getAll(): List<Product> {
        return productDao.getAll()
    }

    // categories
    @WorkerThread
    suspend fun filter(category: String): List<Product> {
        return productDao.filter(category)
    }

    @WorkerThread
    suspend fun deleteAllProducts() {
        productDao.deleteAll()
    }
}