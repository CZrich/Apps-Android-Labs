package com.plataformas.lab05

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.plataformas.lab05.fragments.ButtonFragment
import com.plataformas.lab05.fragments.ImageFragment
import com.plataformas.lab05.fragments.TextFragment
import com.plataformas.lab05.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnText = findViewById<Button>(R.id.btnText)
        val btnImage = findViewById<Button>(R.id.btnImage)
        val btnButton = findViewById<Button>(R.id.btnButton)

        // Fragment inicial
        replaceFragment(HomeFragment())

        // Botones
        btnText.setOnClickListener {
            replaceFragment(TextFragment())
        }

        btnImage.setOnClickListener {
            replaceFragment(ImageFragment())
        }

        btnButton.setOnClickListener {
            replaceFragment(ButtonFragment())
        }
    }

    // Método personalizado con animación
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        transaction.replace(R.id.fragmentContainerView2, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
