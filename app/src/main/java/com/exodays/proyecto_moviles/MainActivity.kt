package com.exodays.proyecto_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicialización de los elementos de la interfaz
        val editTextNombreAlumno = findViewById<EditText>(R.id.editTextNombreAlumno)
        val buttonIniciar = findViewById<Button>(R.id.buttonIniciar)

        // Validación y acción del botón "Iniciar"
        buttonIniciar.setOnClickListener {
            // Obtener el texto ingresado
            val nombreAlumno = editTextNombreAlumno.text.toString().trim()

            // Validaciones del campo "Nombre Alumno"
            if (nombreAlumno.isEmpty()) {
                // Validación de campo vacío
                Toast.makeText(this, "El campo 'Nombre Alumno' no puede estar vacío", Toast.LENGTH_SHORT).show()
            } else if (nombreAlumno.length < 5) {
                // Validación de longitud mínima
                Toast.makeText(this, "El nombre debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                // Si pasa las validaciones, invoca la siguiente vista
                val intent = Intent(this, CalcActivity::class.java)
                intent.putExtra("nombreAlumno", nombreAlumno)
                startActivity(intent)
            }
        }


    }
}
