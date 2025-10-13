package com.plataformas.project
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.plataformas.project.frames.ComunidadFragment
import com.plataformas.project.frames.HomeFragment
import com.plataformas.project.frames.MapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var  bottomNavigation: BottomNavigationView
    //private lateinit var  fragmentTransation:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        //por defaul estar en home

        replaceFragment(HomeFragment())
        //cabia los framges
        bottomNavigation.setOnItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_comunidad->replaceFragment(ComunidadFragment())
                R.id.menu_mapa-> replaceFragment(MapFragment())
            }
            true
        }

    }

    // funcion para cambiar de fragments
    private fun replaceFragment(fragment: Fragment){
      val transaction = supportFragmentManager.beginTransaction()
      transaction.setCustomAnimations(android.R.anim.fade_in,
          android.R.anim.fade_out
          )
        transaction.replace(R.id.fragmentContainerView2,fragment)
        transaction.commit()

    }



}