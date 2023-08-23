package br.com.alura.aluraorgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.aluraorgs.R
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityListProductsBinding
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.menu.ProdutMenuActions
import br.com.alura.aluraorgs.ui.recyclerview.adapter.ListProductAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListProductActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListProductsBinding.inflate(layoutInflater)
    }

    private val productDao by lazy {
        ProductDatabase.instance(this).productDao()
    }

    private val scope = CoroutineScope(Dispatchers.IO)

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
        configureFab()
    }

    override fun onResume() {
        super.onResume()
        configureRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        scope.launch {
            val productsFiltered: List<Product>? = when (item.itemId) {
                R.id.menu_name_asc -> productDao.getOrderByNameAsc()
                R.id.menu_name_desc -> productDao.getOrderByNameDesc()
                R.id.menu_value_asc -> productDao.getOrderByValueAsc()
                R.id.menu_value_desc -> productDao.getOrderByValueDesc()
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
        scope.launch {
            val products = productDao.searchAll()
            withContext(Dispatchers.Main) {
                adapter.reload(products = products)
            }
        }
    }

    private fun configureFab() {
        val floatingButton = binding.mainAction
        floatingButton.setOnClickListener {
            val intent = Intent(this, FormProductActivity::class.java)
            startActivity(intent)
        }
    }
}