package br.com.alura.aluraorgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.aluraorgs.databinding.ActivityLoginBinding
import br.com.alura.aluraorgs.extensions.goTo

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
        configuraBotaoEntrar()
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val user = binding.activityLoginUsuario.text.toString()
            val password = binding.activityLoginSenha.text.toString()
            Log.i("LoginActivity", "onCreate: $user - $password")
            goTo(ListProductActivity::class.java)
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            goTo(FormCreateUserActivity::class.java)
        }
    }

}