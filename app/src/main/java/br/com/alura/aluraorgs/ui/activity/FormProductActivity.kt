package br.com.alura.aluraorgs.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.databinding.ActivityFormProductBinding
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.dialog.FormImageDialog
import java.math.BigDecimal

class FormProductActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormProductBinding.inflate(layoutInflater)
    }

    private val productDao by lazy {
        ProductDatabase.instance(this).productDao()
    }

    private  var imageUrl: String? = null
    private var idProduct = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"


        val name = binding.formTitleValue
        val desc = binding.formDescValue
        val value = binding.formValueValue
        val button = binding.formButton


        intent.getParcelableExtra<Product>("product")?.let {
            idProduct = it.id
            name.setText(it.name)
            desc.setText(it.description)
            value.setText(it.value.toString())
            imageUrl = it.image
            binding.imagePreview.tryLoadImage(it.image, fallbackImageDefault = true)
            title = "Editar Produto"
        }

        setImagePreviewModal()

        button.setOnClickListener {
            saveProduct(
                name = name.text.toString(),
                desc = desc.text.toString(),
                value = value.text.toString()
            )
            Toast.makeText(this, "Produto adicionado com sucesso!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun saveProduct(name: String, desc: String, value: String?): Product {
        val bigValue = when {
            value?.isBlank() == true -> {
                BigDecimal.ZERO
            }

            else -> {
                BigDecimal(value)
            }
        }
        val product = Product(
            id = idProduct,
            name = name,
            description = desc,
            value = bigValue,
            image = imageUrl
        )


        if (idProduct > 0) {
            Log.i("FormProduct", "saveProduct: $product")
            productDao.update(product)
        } else {
            productDao.insert(product)
        }


        return product
    }

    private fun setImagePreviewModal() {
        binding.imagePreview.setOnClickListener {
            FormImageDialog(this).showDialog(imageUrl) {image ->
                imageUrl = image
                binding.imagePreview.tryLoadImage(imageUrl)
            }
        }
    }
}