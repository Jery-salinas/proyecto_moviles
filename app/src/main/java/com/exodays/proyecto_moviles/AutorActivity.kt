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
    private val EDIT_REQUEST_CODE = 1001

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

        // Configuración inicial de la UI
        updateUI()

        // Configurar el botón flotante para abrir EditActivity
        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("k_matricula", binding.txtemail.text.toString())
                putExtra("k_nombre", binding.txtname.text.toString())
                putExtra("k_telefono", binding.txtphone.text.toString())
            }
            startActivityForResult(intent, EDIT_REQUEST_CODE)  // Usamos startActivityForResult
        }

        // Configurar enlaces y acciones
        setupIntent()
    }

    // Manejar la respuesta de EditActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Extraer los datos recibidos
            val matricula = data?.getStringExtra("k_matricula")
            val nombre = data?.getStringExtra("k_nombre")
            val telefono = data?.getStringExtra("k_telefono")

            // Actualizar los campos en la UI
            matricula?.let { binding.txtemail.text = it }
            nombre?.let { binding.txtname.text = it }
            telefono?.let { binding.txtphone.text = it }

            // Si recibes la URI de la imagen, actualiza la imagen (si es necesario)
            val imageUri = data?.getStringExtra("k_image_uri")
            if (!imageUri.isNullOrEmpty()) {
                binding.imgprofile.setImageURI(Uri.parse(imageUri))
            }

            Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
        }
    }

    // Configuración de enlaces (buscar por web, enviar correo, realizar llamada telefónica)
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

    // Método para actualizar la UI con los datos actuales
    private fun updateUI() {
        // Establecer valores predeterminados (puedes actualizar esto con datos reales)
        binding.txtname.text = "Jeremy"
        binding.txtemail.text = "S20004654"
        binding.txtphone.text = "+52 294 456 7890"
        // Si tienes un sitio web, agregarlo aquí
    }

    // Manejar el botón de retroceso
    override fun onSupportNavigateUp(): Boolean {
        finish() // Finaliza la actividad y regresa a la anterior
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
            startActivityForResult(intent, EDIT_REQUEST_CODE)  // Usamos startActivityForResult
        }
        return super.onOptionsItemSelected(item)
    }
}
