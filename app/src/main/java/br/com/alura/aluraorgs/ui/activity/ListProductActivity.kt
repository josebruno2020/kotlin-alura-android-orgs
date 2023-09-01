package br.com.alura.aluraorgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.aluraorgs.R
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityListProductsBinding
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.menu.ProdutMenuActions
import br.com.alura.aluraorgs.ui.recyclerview.adapter.ListProductAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListProductActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListProductsBinding.inflate(layoutInflater)
    }

    private val productDao by lazy {
        ProductDatabase.instance(this).productDao()
    }

    private val adapter by lazy {
        ListProductAdapter(context = this, onClickItemViewListener = {
            val intent = Intent(this, ProductDetailsActivity::class.java).apply {
                putExtra(PRODUCT_ID, it.id)
            }
            startActivity(intent)
        }, onEditClick = { product ->
            ProdutMenuActions(this).editActionButton(product = product, onFinish = {
                startActivity(it)
            })
        }, onRemoveClick = {
            ProdutMenuActions(this).deleteActionButton(product = it, onFinish = {
                configureRecyclerView()
            })
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureRecyclerView()
        configureFab()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            val productsFiltered: List<Product>? = when (item.itemId) {
                R.id.menu_name_asc -> productDao.getOrderByNameAsc().firstOrNull()
                R.id.menu_name_desc -> productDao.getOrderByNameDesc().firstOrNull()
                R.id.menu_value_asc -> productDao.getOrderByValueAsc().firstOrNull()
                R.id.menu_value_desc -> productDao.getOrderByValueDesc().firstOrNull()
                else -> null
            }

            withContext(Dispatchers.Main) {
                productsFiltered?.let {
                    adapter.reload(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureRecyclerView() {
        val list = binding.list
        searchProducts()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        binding.swipeListRefresh.setOnRefreshListener {
            searchProducts()
            binding.swipeListRefresh.isRefreshing = false
        }
    }

    private fun searchProducts() {
        val job = lifecycleScope.launch {
            productDao.searchAll().collect {
                adapter.reload(it)
            }
        }

//        job.cancel()
    }

    private suspend fun searchAllProducts() =
        withContext(Dispatchers.IO) {
            productDao.searchAll()
        }


    private fun configureFab() {
        val floatingButton = binding.mainAction
        floatingButton.setOnClickListener {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }
    }
}