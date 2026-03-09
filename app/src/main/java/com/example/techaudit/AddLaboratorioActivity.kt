package com.example.techaudit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techaudit.databinding.ActivityAddLaboratorioBinding
import com.example.techaudit.model.Laboratorio
import com.example.techaudit.ui.LaboratorioViewModel

class AddLaboratorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddLaboratorioBinding

    private val viewModel: LaboratorioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddLaboratorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotonGuardar()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configurarBotonGuardar(){
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val edificio = binding.etEdificio.text.toString()

            if(nombre.isEmpty()|| edificio.isEmpty()){
                Toast.makeText(this,"Debe llenar los campos",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val laboratorio = Laboratorio(
                nombre = nombre,
                edificio = edificio
            )
            viewModel.insert(laboratorio)
            Toast.makeText(this,"Laboratorio guardado",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}