package br.com.alura.aluraorgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.aluraorgs.R
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityProductDetailsBinding
import br.com.alura.aluraorgs.extensions.formatBrValue
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.dialog.ConfirmDialog

class ProductDetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }

    private val productDao by lazy {
        ProductDatabase.instance(this).productDao()
    }

    private lateinit var product: Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getParcelableExtra<Product>("product")?.let {
            product = it
            setViewData(product = product)
        } ?: finish()
    }

    override fun onResume() {
        //TODO atualizar produto em memoria
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i("menu", "onCreateOptionsMenu: $menu")
        menuInflater.inflate(R.menu.menu_details_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val TAG = "menu"
        if (!::product.isInitialized) {
            return true
        }
        return when (item.itemId) {
            R.id.menu_details_delete -> {
                ConfirmDialog(this).showDialog(onClickSuccess = {
                    productDao.delete(product)
                    Toast.makeText(this, "Produto removido com sucesso", Toast.LENGTH_LONG)
                        .show()
                    finish()
                })
                true
            }
            R.id.menu_details_edit -> {
                Intent(this, FormProductActivity::class.java).apply {
                    putExtra("product", product)
                    startActivity(this)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setViewData(product: Product) {
        binding.detailsImage.tryLoadImage(product.image, fallbackImageDefault = true)
        binding.detailsValue.text = product.value.formatBrValue()
        binding.detailsTitle.text = product.name
        binding.detailsDescription.text = product.description
    }
}