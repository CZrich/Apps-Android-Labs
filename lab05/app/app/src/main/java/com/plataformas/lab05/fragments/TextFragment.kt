package com.plataformas.lab05.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.plataformas.lab05.R

class TextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_text, container, false)

        // Click en card de La Catedral
        view.findViewById<CardView>(R.id.cardCatedral).setOnClickListener {
            openDetail(
                title = "La Catedral",
                description = "La Catedral Basílica de Arequipa es uno de los primeros monumentos religiosos del siglo XVII en la ciudad. Ocupa todo el lado norte de la Plaza de Armas. Su fachada está compuesta de setenta columnas con bóvedas. Tres portadas la decoran, la principal y dos laterales, con arcos de medio punto.\n\nEn su interior destacan el púlpito neogótico francés tallado por Rigot en 1879, un órgano monumental belga construido por Loret en 1870 y vitrales góticos.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card del Monasterio
        view.findViewById<CardView>(R.id.cardMonasterio).setOnClickListener {
            openDetail(
                title = "Monasterio de Santa Catalina",
                description = "El Monasterio de Santa Catalina es un complejo turístico religioso ubicado en el centro histórico de Arequipa. Fue construido en 1579 y ocupa aproximadamente 20,000 m². Es considerado uno de los conjuntos arquitectónicos más importantes del Perú.\n\nSus calles llevan nombres de ciudades españolas y están pintadas en colores vivos: azul, ocre y ladrillo.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card de Plaza de Armas
        view.findViewById<CardView>(R.id.cardPlaza).setOnClickListener {
            openDetail(
                title = "Plaza de Armas",
                description = "La Plaza de Armas de Arequipa es una de las más bellas del Perú. Está rodeada por tres portales de construcciones de piedra de sillar: Portal de la Municipalidad, Portal del Cabildo y Portal de San Agustín.\n\nEn el centro se encuentra una pileta de bronce coronada por la figura de un soldado del siglo XVI, llamada El Tuturutu.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card del Volcán Misti
        view.findViewById<CardView>(R.id.cardMisti).setOnClickListener {
            openDetail(
                title = "Volcán Misti",
                description = "El Misti es un volcán activo ubicado en el sur del Perú, cerca de la ciudad de Arequipa. Tiene una altura de 5,822 metros sobre el nivel del mar. Es uno de los símbolos más representativos de Arequipa y parte del paisaje natural de la Ciudad Blanca.\n\nSu nombre proviene del quechua 'Misti' que significa 'señor' o 'caballero'.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        return view
    }

    private fun openDetail(title: String, description: String, imageRes: Int) {
        val detailFragment = DetailFragment.newInstance(title, description, imageRes)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainerView2, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}