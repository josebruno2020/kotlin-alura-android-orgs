package br.com.alura.aluraorgs.ui.menu

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleCoroutineScope
import br.com.alura.aluraorgs.database.ProductDatabase
import br.com.alura.aluraorgs.model.Product
import br.com.alura.aluraorgs.ui.activity.FormProductActivity
import br.com.alura.aluraorgs.ui.activity.PRODUCT_ID
import br.com.alura.aluraorgs.ui.dialog.ConfirmDialog
import br.com.alura.aluraorgs.ui.toast.ToastMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProdutMenuActions(
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope
) {
    private val productDao by lazy {
        ProductDatabase.instance(context).productDao()
    }

    fun editActionButton(product: Product?, onFinish: (intent: Intent) -> Unit = {}) {
        Intent(context, FormProductActivity::class.java).apply {
            product?.let {
                putExtra(PRODUCT_ID, it.id)
                onFinish(this)
            }
        }
    }
    fun deleteActionButton(product: Product?, onFinish: () -> Unit = {}) {
        ConfirmDialog(context).showDialog(onClickSuccess = {
            product?.let {
                this.lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        productDao.delete(it)
                    }
                    ToastMessage(context).showToastMessage("Produto removido com sucesso")
                    onFinish()
                }
            }
        })
    }
}