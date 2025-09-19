package com.plataformas.studentconf

import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge


import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
data class Attendee(
    val names: String,
    val lastNames: String,
    val email: String,
    val phone: String,
    val carrer:String,
    val bloodGroup: String
) {
    // convertir a línea de texto (escape simple)
    fun toLine(): String {
        // usamos '|' como separador; podrías normalizar eliminando pipes del texto
        return listOf(names, lastNames, email,phone,carrer, bloodGroup).joinToString("|")
    }

    companion object {
        fun fromLine(line: String): Attendee? {
            if (line.isBlank()) return null
            val parts = line.split("|")
            if (parts.size < 6) return null
            return Attendee(parts[0], parts[1], parts[2], parts[3], parts[4],parts[5])
        }
    }
}
class MainActivity : AppCompatActivity() {
    private val FILENAME = "asistentes.txt"
    private val TAG = "ConferenciaApp"

    private lateinit var editStudentName: EditText
    private  lateinit var editStudentSurname: EditText
    private lateinit var  editEmail: EditText
//    editCarrer
//    editStudentPhone
//    editGH
    private lateinit var  editStudentPhone: EditText
    private lateinit var  editCarrer: EditText
    private lateinit var editGH: EditText

    private lateinit var  btnRegistrar: Button
    private lateinit var  btnInscritos: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        editStudentName =findViewById(R.id.editStudentName)
        editStudentSurname=findViewById(R.id.editStudentSurname)
        editEmail = findViewById(R.id.editEmail)
        editStudentPhone = findViewById(R.id.editStudentPhone)
        editCarrer = findViewById(R.id.editCarrer)
        editGH = findViewById(R.id.editGH)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnInscritos  = findViewById(R.id.btnInscritos)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        btnRegistrar.setOnClickListener {
            val attendee = Attendee(
                names = editStudentName.text.toString().trim(),
                lastNames = editStudentSurname.text.toString().trim(),
                email = editEmail.text.toString().trim(),
                phone =editStudentPhone.text.toString().trim(),
                carrer=editCarrer.text.toString().trim(),
                bloodGroup = editGH.text.toString().trim()
            )

            if (attendee.names.isEmpty() || attendee.lastNames.isEmpty()) {
                Toast.makeText(this, "Completa al menos nombres y apellidos", Toast.LENGTH_SHORT).show()
            } else {
                val success = appendAttendeeToFile(attendee)
                if (success) {
                    Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show()
                    // limpiar campos opcional
                    editStudentName.text.clear()
                    editStudentSurname.text.clear()
                    editEmail.text.clear()
                    editCarrer.text.clear()
                    editStudentPhone.text.clear()
                    editGH.text.clear()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnInscritos.setOnClickListener {
            val attendees = readAttendeesFromFile()
            if (attendees.isEmpty()) {
                Log.d("TAG", "No hay registros en el archivo.")
                Toast.makeText(this, "Archivo vacío o no existe", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("TAG", "=== Lista de asistentes (${attendees.size}) ===")
                attendees.forEachIndexed { idx, a ->
                    Log.d("TAG", "${idx + 1}: ${a.names} ${a.lastNames}, email=${a.email},carrera=${a.carrer}, tel=${a.phone}, sangre=${a.bloodGroup}")
                }
                Toast.makeText(this, "Leído ${attendees.size} registros (ver Logcat)", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun appendAttendeeToFile(attendee: Attendee): Boolean {
        return try {
            // openFileOutput en MODE_APPEND para añadir sin borrar
            openFileOutput(FILENAME, MODE_APPEND).use { fos ->
                OutputStreamWriter(fos).use { writer ->
                    writer.append(attendee.toLine())
                    writer.append("\n")
                    writer.flush()
                }
            }
            true
        } catch (e: Exception) {
            Log.e("TAG", "Error guardando archivo: ${e.message}", e)
            false
        }
    }

    private fun readAttendeesFromFile(): List<Attendee> {
        val list = mutableListOf<Attendee>()
        try {
            // openFileInput lanza FileNotFoundException si no existe (lo manejamos)
            openFileInput(FILENAME).use { fis ->
                InputStreamReader(fis).use { isr ->
                    BufferedReader(isr).useLines { lines ->
                        lines.forEach { line ->
                            Attendee.fromLine(line)?.let { list.add(it) }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "Error leyendo archivo: ${e.message}")
        }
        return list
    }
}



