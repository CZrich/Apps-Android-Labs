package com.plataformas.lab05.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.plataformas.lab05.R

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image, container, false)

        // Click en card 1: Cañón del Colca
        view.findViewById<CardView>(R.id.cardImage1).setOnClickListener {
            openDetail(
                title = "Cañón del Colca",
                description = "El Cañón del Colca es uno de los más profundos del mundo, con más de 3,400 metros de profundidad. Es el hogar del majestuoso cóndor andino y ofrece paisajes impresionantes con terrazas pre-incas, pueblos tradicionales y aguas termales.\n\nEl mejor lugar para observar el vuelo de los cóndores es la Cruz del Cóndor, donde estas magníficas aves aprovechan las corrientes térmicas para elevarse.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card 2: Mirador de Yanahuara
        view.findViewById<CardView>(R.id.cardImage2).setOnClickListener {
            openDetail(
                title = "Mirador de Yanahuara",
                description = "El Mirador de Yanahuara es uno de los lugares más emblemáticos de Arequipa. Desde aquí se puede apreciar una vista panorámica de la ciudad blanca y los tres volcanes que la rodean: Misti, Chachani y Pichu Pichu.\n\nEl mirador cuenta con arcos de sillar donde están grabados versos de poetas arequipeños ilustres.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card 3: Ruta del Sillar
        view.findViewById<CardView>(R.id.cardImage3).setOnClickListener {
            openDetail(
                title = "Ruta del Sillar",
                description = "La Ruta del Sillar es un recorrido por las canteras de donde se extrae el sillar, la piedra volcánica blanca característica de Arequipa. Aquí se puede observar el trabajo artesanal de los talladores y las impresionantes esculturas talladas en las paredes de piedra.\n\nEs una experiencia única que conecta con la tradición arquitectónica de la Ciudad Blanca.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card 4: Volcán Chachani
        view.findViewById<CardView>(R.id.cardImage4).setOnClickListener {
            openDetail(
                title = "Volcán Chachani",
                description = "El Chachani es el volcán más alto de la región con 6,075 metros sobre el nivel del mar. Es considerado uno de los seismiles más accesibles del mundo y es popular entre montañistas de todos los niveles.\n\nSu nombre proviene del quechua 'chachani' que significa 'el de la pollera' por su forma cónica.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card 5: Salinas y Aguada Blanca
        view.findViewById<CardView>(R.id.cardImage5).setOnClickListener {
            openDetail(
                title = "Salinas y Aguada Blanca",
                description = "La Reserva Nacional Salinas y Aguada Blanca es un área protegida ubicada entre Arequipa y Moquegua. Alberga especies como vicuñas, alpacas, flamencos y tarucas en un ecosistema de puna.\n\nSus lagunas de aguas cristalinas reflejan los volcanes circundantes, creando paisajes de ensueño.",
                imageRes = R.drawable.ic_launcher_background
            )
        }

        // Click en card 6: Molino de Sabandia
        view.findViewById<CardView>(R.id.cardImage6).setOnClickListener {
            openDetail(
                title = "Molino de Sabandia",
                description = "El Molino de Sabandia es una construcción del siglo XVIII hecha completamente de sillar. Fue restaurado en 1973 y actualmente funciona mostrando la técnica tradicional de molienda de granos.\n\nRodeado de bellos paisajes campestres y andenerías, es un lugar perfecto para el turismo vivencial.",
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