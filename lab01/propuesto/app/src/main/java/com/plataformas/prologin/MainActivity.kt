package com.plataformas.prologin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var editUserNameText: EditText
    private lateinit var editPasswordText: EditText
    private lateinit var  btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets}
        editUserNameText = findViewById(R.id.editUserNameText)
        editPasswordText =findViewById(R.id.editPasswordText)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {

            if(editUserNameText.text.toString() == "admin" && editPasswordText.text.toString() == "admin"){

                Log.d("TAG","Bienvendio a la app")
            }else{
                Log.d("TAG","Error  de autenticación")
            }
        }


    }
}