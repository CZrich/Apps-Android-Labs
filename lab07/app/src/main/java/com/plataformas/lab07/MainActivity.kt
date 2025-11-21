package com.plataformas.lab07

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BuildingAdapter
    private lateinit var etFilter: EditText
    private val buildingsList = mutableListOf<Building>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvBuildings)
        etFilter = findViewById(R.id.etFilter)

        loadDataFromAssets()
        setupRecyclerView()
        setupFilter()
    }

    private fun loadDataFromAssets() {
        val assetManager = assets
        val inputStream = assetManager.open("buildings.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.forEach { line ->
                val parts = line.split("|")
                if (parts.size == 4) {
                    val imageRes = getImageResource(parts[3]) // Mapea nombre a ID drawable
                    buildingsList.add(Building(parts[0], parts[1], parts[2], imageRes))
                }
            }
        }
    }

    private fun getImageResource(imageName: String): Int {
        // Mapeo simple de nombres a drawables (agrega tus imágenes en res/drawable)
        val resources = mapOf(
            "tower_eiffel" to R.drawable.ic_launcher_background, // Placeholder
            "colosseum" to R.drawable.ic_launcher_foreground,
            "taj_mahal" to R.drawable.ic_launcher_background,
            "pyramids" to R.drawable.ic_launcher_foreground,
            "machu_picchu" to R.drawable.ic_launcher_background
        )
        return resources[imageName] ?: R.drawable.ic_launcher_background
    }

    private fun setupRecyclerView() {
        adapter = BuildingAdapter(buildingsList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupFilter() {
        etFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter?.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}