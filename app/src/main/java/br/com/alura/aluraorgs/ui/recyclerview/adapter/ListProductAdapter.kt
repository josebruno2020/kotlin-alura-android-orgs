package br.com.alura.aluraorgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraorgs.databinding.ProductItemBinding
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product
import java.text.NumberFormat
import java.util.Locale

class ListProductAdapter(
    private val context: Context,
    products: List<Product>
) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    private val dataSet = products.toMutableList()

    class ViewHolder(
        private val binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun showItem(product: Product) {
            val name = binding.pTitle
            name.text = product.name

            val desc = binding.description
            desc.text = product.description

            val v = binding.value
            val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            v.text = currencyFormat.format(product.value)

            if (product.image.isNullOrEmpty()) {
                binding.productImage.visibility = View.GONE
            }


            binding.productImage.tryLoadImage(product.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = dataSet[position]
        holder.showItem(product)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reload(products: List<Product>) {
        this.dataSet.clear()
        this.dataSet.addAll(products)
        notifyDataSetChanged()
    }

}