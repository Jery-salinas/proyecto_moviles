package com.exodays.proyecto_moviles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.math.sqrt

class CalcActivity : AppCompatActivity() {
    private var nombreAlumno: String? = null
    private lateinit var editTextNumero1: EditText
    private lateinit var editTextNumero2: EditText
    private lateinit var textViewResultado: TextView
    private lateinit var buttonSumar: Button
    private lateinit var buttonRaiz: Button
    private var resultado: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        // Configurar Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Recibir el nombre del alumno desde el intent
        nombreAlumno = intent.getStringExtra("nombreAlumno")
        supportActionBar?.title = nombreAlumno ?: "Usuario"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inicializar vistas
        editTextNumero1 = findViewById(R.id.editTextNumero1)
        editTextNumero2 = findViewById(R.id.editTextNumero2)
        textViewResultado = findViewById(R.id.textViewResultado)
        buttonSumar = findViewById(R.id.buttonSumar)
        buttonRaiz = findViewById(R.id.buttonRaiz)

        // Configurar botón de suma
        buttonSumar.setOnClickListener {
            if (validarCampos()) {
                val numero1 = editTextNumero1.text.toString().toDouble()
                val numero2 = editTextNumero2.text.toString().toDouble()
                resultado = numero1 + numero2
                textViewResultado.text = "Resultado: $resultado"
            }
        }

        // Configurar botón de raíz cuadrada
        buttonRaiz.setOnClickListener {
            if (resultado != 0.0) {
                val raiz = sqrt(resultado)
                textViewResultado.text = "Raíz del resultado: $raiz"
            } else {
                Toast.makeText(this, "Realiza una suma primero.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Validar que los campos no estén vacíos y contengan números
    private fun validarCampos(): Boolean {
        val numero1 = editTextNumero1.text.toString()
        val numero2 = editTextNumero2.text.toString()

        return when {
            numero1.isEmpty() || numero2.isEmpty() -> {
                Toast.makeText(this, "Por favor ingresa ambos números.", Toast.LENGTH_SHORT).show()
                false
            }
            !esNumeroValido(numero1) || !esNumeroValido(numero2) -> {
                Toast.makeText(this, "Ingresa valores numéricos válidos.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    // Verificar si el texto es un número válido (incluyendo decimales)
    private fun esNumeroValido(texto: String): Boolean {
        return texto.toDoubleOrNull() != null
    }

    // Inflar el menú en la actividad
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Manejar las acciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                // Llamar a la actividad "Acerca de"
                val intent = Intent(this, AutorActivity::class.java)
                startActivity(intent)
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
