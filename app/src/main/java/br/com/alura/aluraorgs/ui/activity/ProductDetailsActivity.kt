package br.com.alura.aluraorgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.aluraorgs.databinding.ActivityProductDetailsBinding
import br.com.alura.aluraorgs.extensions.formatBrValue
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product

class ProductDetailsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()


        val extrasData = intent.extras


        val product = extrasData?.getParcelable<Product>("product")
        Log.i("ProductDetails", "onCreate: $product")

        if (product == null) {
            finish()
        }


        setViewData(product as Product)
    }

    private fun setViewData(product: Product) {
        binding.detailsImage.tryLoadImage(product.image, fallbackImageDefault = true)
        binding.detailsValue.text = product.value.formatBrValue()
        binding.detailsTitle.text = product.name
        binding.detailsDescription.text = product.description
    }
}