package br.com.alura.aluraorgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.aluraorgs.databinding.ActivityFormCreateUserBinding
import br.com.alura.aluraorgs.model.User


class FormCreateUserActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormCreateUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureSaveButton()
    }

    private fun configureSaveButton() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val newUser = createUser()
            Log.i("CadastroUsuario", "onCreate: $newUser")
            finish()
        }
    }

    private fun createUser(): User {
        val user = binding.activityFormularioCadastroUsuario.text.toString()
        val name = binding.activityFormularioCadastroNome.text.toString()
        val password = binding.activityFormularioCadastroSenha.text.toString()
        return User(user, name, password)
    }
}