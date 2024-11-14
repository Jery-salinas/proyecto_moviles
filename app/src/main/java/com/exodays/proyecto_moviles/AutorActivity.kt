package com.exodays.proyecto_moviles

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exodays.proyecto_moviles.databinding.ActivityAutorBinding

class AutorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAutorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el título de la actividad
        supportActionBar?.title = "Acerca del Desarrollador"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ajustar el padding para la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupIntent()
        updateUI()

        // Configurar el botón flotante para abrir EditActivity
        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("k_matricula", binding.txtemail.text.toString())
                putExtra("k_nombre", binding.txtname.text.toString())
                putExtra("k_telefono", binding.txtphone.text.toString())
            }
            startActivity(intent)
        }
    }

    private fun setupIntent() {
        // Buscar por web (cuando se haga clic en el nombre)
        binding.txtname.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, binding.txtname.text)
            }
            startActivity(intent)
        }

        // Enviar correo (cuando se haga clic en el correo)
        binding.txtemail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, binding.txtemail.text.toString())
                putExtra(Intent.EXTRA_SUBJECT, "Asunto TAM")
                putExtra(Intent.EXTRA_TEXT, "Texto de correo aquí")
            }
            startActivity(intent)
        }

        // Realizar llamada telefónica (cuando se haga clic en el teléfono)
        binding.txtphone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val tel = binding.txtphone.text
                data = Uri.parse("tel:$tel")
            }
            startActivity(intent)
        }
    }

    // Manejar el botón de retroceso
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Métodos de menú, si es necesario agregar opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit) {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("k_matricula", binding.txtemail.text.toString())
                putExtra("k_nombre", binding.txtname.text.toString())
                putExtra("k_telefono", binding.txtphone.text.toString())
            }
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        // Establecer los valores de las vistas
        binding.txtname.text = "Jeremy"
        binding.txtemail.text = "S20004654"
        binding.txtphone.text = "+52 294 456 7890"
        // Si tienes un sitio web, agregarlo aquí
    }
}
