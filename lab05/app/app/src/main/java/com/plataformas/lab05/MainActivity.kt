package com.plataformas.lab05

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.plataformas.lab05.fragments.ButtonFragment
import com.plataformas.lab05.fragments.ImageFragment
import com.plataformas.lab05.fragments.TextFragment
import com.plataformas.lab05.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var btnText: Button
    private lateinit var btnImage: Button
    private lateinit var btnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajustar padding para edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar botones
        btnText = findViewById(R.id.btnText)
        btnImage = findViewById(R.id.btnImage)
        btnButton = findViewById(R.id.btnButton)

        // Cargar fragment inicial (HomeFragment)
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Configurar clicks de botones
        btnText.setOnClickListener {
            replaceFragment(TextFragment())
            updateButtonSelection(btnText)
        }

        btnImage.setOnClickListener {
            replaceFragment(ImageFragment())
            updateButtonSelection(btnImage)
        }

        btnButton.setOnClickListener {
            replaceFragment(ButtonFragment())
            updateButtonSelection(btnButton)
        }
    }

    // Método para reemplazar fragments con animación
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        transaction.replace(R.id.fragmentContainerView2, fragment)
        transaction.commit()
    }

    // Método opcional para resaltar el botón activo
    private fun updateButtonSelection(selectedButton: Button) {
        // Resetear todos los botones
        btnText.isSelected = false
        btnImage.isSelected = false
        btnButton.isSelected = false

        // Marcar el botón seleccionado
        selectedButton.isSelected = true
    }

    // Manejar el botón de retroceso
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}