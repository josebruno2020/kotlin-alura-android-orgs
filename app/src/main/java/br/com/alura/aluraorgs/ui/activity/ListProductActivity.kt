package br.com.alura.aluraorgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.aluraorgs.dao.ProductsDao
import br.com.alura.aluraorgs.databinding.ActivityListProductsBinding
import br.com.alura.aluraorgs.ui.recyclerview.adapter.ListProductAdapter

class ListProductActivity : AppCompatActivity() {
    private val dao = ProductsDao()

    private val binding by lazy {
        ActivityListProductsBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListProductAdapter(context = this, products = dao.searchAll())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val floatingButton = binding.mainAction
        floatingButton.setOnClickListener {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        searchProducts()
    }

    private fun searchProducts() {
        val list = binding.list
        adapter.reload(dao.searchAll())
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)
    }
}