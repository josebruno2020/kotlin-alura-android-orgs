package br.com.alura.aluraorgs.dao

import br.com.alura.aluraorgs.model.Product
import java.math.BigDecimal

class ProductsDao {

    fun add(product: Product) {
        products.add(product)
    }

    fun searchAll(onComplete: () -> Unit = {}): List<Product> {
        val products = products.toList()
        onComplete()
        return products
    }

    companion object {
        private val products = mutableListOf<Product>(
            Product(
                name = "Salada de frutas",
                description = "Muito gostosa",
                value = BigDecimal("10.54")
            )
        )
    }
}