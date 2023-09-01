package br.com.alura.aluraorgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.aluraorgs.R
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityProductDetailsBinding
import br.com.alura.aluraorgs.extensions.formatBrValue
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.menu.ProdutMenuActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }

    private val productDao by lazy {
        ProductDatabase.instance(this).productDao()
    }

    private var idProduct: Long = 0L

    private var product: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        idProduct = intent.getLongExtra(PRODUCT_ID, 0L)

        setViewData(idProduct = idProduct)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i("menu", "onCreateOptionsMenu: $menu")
        menuInflater.inflate(R.menu.menu_details_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_details_delete -> {
                ProdutMenuActions(this).deleteActionButton(product = product, onFinish = {
                    finish()
                })
                true
            }
            R.id.menu_details_edit -> {
                ProdutMenuActions(this).editActionButton(product = product, onFinish = {
                    startActivity(it)
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setViewData(idProduct: Long) {
        lifecycleScope.launch {
            productDao.getById(idProduct).collect { p ->
                product = p
                product?.let {
                    withContext(Dispatchers.Main) {
                        fillData(it)
                    }
                } ?: finish()
            }
        }
    }

    private fun fillData(product: Product) {
        binding.detailsImage.tryLoadImage(product.image, fallbackImageDefault = true)
        binding.detailsValue.text = product.value.formatBrValue()
        binding.detailsTitle.text = product.name
        binding.detailsDescription.text = product.description
    }
}