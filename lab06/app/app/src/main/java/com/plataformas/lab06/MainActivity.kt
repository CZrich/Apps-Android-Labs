package com.plataformas.lab06

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.plataformas.lab06.fragments.BuildingPlanFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if(savedInstanceState ==null){
            supportFragmentManager.commit {
                replace(R.id.fragment_container, BuildingPlanFragment())
            }
        }

    }
}