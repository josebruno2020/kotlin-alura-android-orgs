package br.com.alura.aluraorgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.aluraorgs.R
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityProductDetailsBinding
import br.com.alura.aluraorgs.extensions.formatBrValue
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.menu.ProdutMenuActions

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
    }

    override fun onResume() {
        super.onResume()
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
        product = productDao.getById(idProduct)
        product?.let {
            binding.detailsImage.tryLoadImage(it.image, fallbackImageDefault = true)
            binding.detailsValue.text = it.value.formatBrValue()
            binding.detailsTitle.text = it.name
            binding.detailsDescription.text = it.description
        } ?: finish()
    }
}