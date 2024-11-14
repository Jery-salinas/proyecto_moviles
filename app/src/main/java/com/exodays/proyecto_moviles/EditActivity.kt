package com.exodays.proyecto_moviles

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exodays.proyecto_moviles.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar los datos pasados por Intent
        binding.tieMatricula.setText(intent.extras?.getString("k_matricula"))
        binding.tieNombre.setText(intent.extras?.getString("k_nombre"))
        binding.tieTelefono.setText(intent.extras?.getString("k_telefono"))

        // Configurar la selección de imagen
        setupImageSelection()

    }

    private fun setupImageSelection() {
        binding.btnImgSelect.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.imgprofile.setImageURI(imageUri) // Establece la imagen en el ImageView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_send -> {
                if (validateFields()) {
                    sendData()
                    showConfirmationDialog()
                } else {
                    Toast.makeText(this, "Verifica tus datos!!", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.action_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendData() {
        val intent = Intent()
        // Extraer los datos del formulario
        intent.putExtra("k_matricula", binding.tieMatricula.text.toString())
        intent.putExtra("k_nombre", binding.tieNombre.text.toString())
        intent.putExtra("k_telefono", binding.tieTelefono.text.toString())

        // Añadir el URI de la imagen seleccionada, si está disponible
        imageUri?.let {
            intent.putExtra("k_image_uri", it.toString())
        }

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun validateFields(): Boolean {
        var isValid = true

        // Validar Matrícula
        val matricula = binding.tieMatricula.text.toString().trim()
        val matriculaPattern = "^S\\d{8}$".toRegex()
        if (matricula.isEmpty() || !matricula.matches(matriculaPattern)) {
            binding.tilMatricula.error = "Matrícula incorrecta, debe empezar con 'S' seguido de 8 dígitos"
            isValid = false
        } else {
            binding.tilMatricula.error = null
        }

        // Validar Nombre
        val name = binding.tieNombre.text.toString().trim()
        if (name.isEmpty() || name.length < 3) {
            binding.tilNombre.error = "Nombre incorrecto, debe tener al menos 3 caracteres"
            isValid = false
        } else {
            binding.tilNombre.error = null
        }

        // Validar Teléfono
        val phone = binding.tieTelefono.text.toString().trim()
        if (phone.isEmpty() || !phone.matches("\\d{10}".toRegex())) {
            binding.tilTelefono.error = "Teléfono incorrecto, debe tener exactamente 10 dígitos"
            isValid = false
        } else {
            binding.tilTelefono.error = null
        }

        return isValid
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Listo")
            .setMessage("Datos modificados")
            .setPositiveButton("Terminar") { dialog, _ ->
                dialog.dismiss()
                clearFields()
            }
            .show()
    }

    private fun clearFields() {
        binding.tieMatricula.text?.clear()
        binding.tieNombre.text?.clear()
        binding.tieTelefono.text?.clear()
    }
}
