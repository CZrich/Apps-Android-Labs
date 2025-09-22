package com.plataformas.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.plataformas.loginapp.models.User

class MainActivity : AppCompatActivity() {
    private val  FILENAME = "accounts.txt"

    private lateinit var  btnLogin: Button
    private lateinit var  btnCrearCuenta: Button
    private lateinit var  editUser: EditText
    private lateinit var editLoginPassword: EditText

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val jsonStr = data?.getStringExtra("newUser")
            if (!jsonStr.isNullOrEmpty()) {
                val user = User.fromJson(jsonStr)
                saveUser(user)
                Toast.makeText(this, "Cuenta creada: ${user.usuario}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)




        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        btnLogin = findViewById(R.id.btnLogin)
        editUser = findViewById(R.id.editUser)
        editLoginPassword = findViewById(R.id.editLoginPassword)

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            //startActivity(intent)
            launcher.launch(intent)
        }


        btnLogin.setOnClickListener {
            val userInput = editUser.text.toString()
            val passwordInput = editLoginPassword.text.toString()
            Log.d("TAG","DATOS -> ${userInput}  ${passwordInput}")

            editUser.text.clear()
            editLoginPassword.text.clear()
            val userFound = checkLogin(userInput, passwordInput)
            if (userFound != null) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("usuario", userFound.usuario)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Cuenta no encontrada", Toast.LENGTH_SHORT).show()
            }


        }




    }

    private fun saveUser(user: User) {
        openFileOutput(FILENAME, MODE_APPEND).use { fos ->
            fos.write((user.toJson() + "\n").toByteArray())
        }
    }

    private fun checkLogin(username: String, password: String): User? {
        try {
            openFileInput(FILENAME).bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val u = User.fromJson(line)
                    if (u.usuario == username && u.password == password) {
                        return u
                    }
                }
            }
        } catch (e: Exception) {
            // archivo vacío o inexistente
        }
        return null
    }
}