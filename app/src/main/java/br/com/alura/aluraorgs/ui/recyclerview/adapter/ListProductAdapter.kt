package br.com.alura.aluraorgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraorgs.R
import br.com.alura.aluraorgs.databinding.ProductItemBinding
import br.com.alura.aluraorgs.extensions.formatBrValue
import br.com.alura.aluraorgs.extensions.tryLoadImage
import br.com.alura.aluraorgs.model.Product

class ListProductAdapter(
    private val context: Context,
    products: List<Product> = listOf(),
    var onClickItemViewListener: (product: Product) -> Unit = {},
    var onEditClick: (product: Product) -> Unit = {},
    var onRemoveClick: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ListProductAdapter.ViewHolder>() {

    private val dataSet = products.toMutableList()

    inner class ViewHolder(
        private val binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onClickItemViewListener(product)
                }
            }

            itemView.setOnLongClickListener {
                PopupMenu(context, itemView)
                    .apply {
                        menuInflater.inflate(R.menu.menu_details_product, menu)
                        setOnMenuItemClickListener(this@ViewHolder)
                    }.show()
                true
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when(item.itemId) {
                    R.id.menu_details_edit -> onEditClick(product)
                    R.id.menu_details_delete -> onRemoveClick(product)
                }
            }
            return true
        }

        fun showItem(
            product: Product,
        ) {
            this.product = product

            val name = binding.pTitle
            name.text = product.name

            val desc = binding.description
            desc.text = product.description

            val v = binding.value
            v.text = product.value.formatBrValue()

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
