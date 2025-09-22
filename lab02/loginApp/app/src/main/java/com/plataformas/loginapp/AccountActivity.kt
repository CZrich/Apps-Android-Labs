package com.plataformas.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.plataformas.loginapp.models.User
import java.util.logging.LogManager

class AccountActivity : AppCompatActivity() {

    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button
    private lateinit var  editNombres: EditText
    private lateinit var  editApellidos: EditText
    private lateinit var  editCorreo: EditText
    private lateinit var  editTelefono: EditText
    private lateinit var  editUsuario: EditText
    private lateinit var  editPassword: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)


        btnAceptar = findViewById(R.id.btnAceptar)
        btnCancelar = findViewById(R.id.btnCancelar)
        editUsuario = findViewById(R.id.editUsuario)
        editCorreo = findViewById(R.id.editCorreo)
        editPassword = findViewById(R.id.editPassword)
        editTelefono = findViewById(R.id.editTelefono)
        editNombres = findViewById(R.id.editNombres)
        editApellidos  = findViewById(R.id.editApellidos)


        btnAceptar.setOnClickListener {
            val user = User(
                editNombres.text.toString(),
                editApellidos.text.toString(),
                editCorreo.text.toString(),
                editTelefono.text.toString(),
                editUsuario.text.toString(),
                editPassword.text.toString()
            )

            val resultIntent = Intent().apply {
                putExtra("newUser", user.toJson())
            }
            Log.d("TAG","datos ==> ${resultIntent}")
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }



    }
}