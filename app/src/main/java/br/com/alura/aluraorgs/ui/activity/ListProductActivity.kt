package br.com.alura.aluraorgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityListProductsBinding
import br.com.alura.aluraorgs.ui.recyclerview.adapter.ListProductAdapter

class ListProductActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListProductsBinding.inflate(layoutInflater)
    }

    private val productDao by lazy {
        ProductDatabase.instance(this).productDao()
    }

    val TAG = "ListProduct"

    private val adapter by lazy {
        ListProductAdapter(context = this, onClickItemViewListener =  {
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra("product", it)
            }
            startActivity(intent)
        }, onEditClick = {
            Log.i(TAG, "botao editar")
        }, onRemoveClick = {
            Log.i(TAG, "botao remover")
        })
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
        adapter.reload(productDao.searchAll())
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        binding.swipeListRefresh.setOnRefreshListener {
            adapter.reload(productDao.searchAll())
            binding.swipeListRefresh.isRefreshing = false
        }
    }
}