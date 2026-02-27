package com.example.techaudit

import android.app.Activity
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techaudit.adapter.AuditAdapter
import com.example.techaudit.data.AuditDatabase
import com.example.techaudit.databinding.ActivityMainBinding
import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.AuditStatus
import com.example.techaudit.ui.theme.TechAuditTheme
import java.util.UUID

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: AuditAdapter
    private lateinit var dataBase: AuditDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataBase = (application as TechAuditApp).database)

        setupRecyclerView()

        cargarDatosdeBaseDeDatos()

        binding.fabAgregar.setOnClickListener {
            insertarRegistro()
        }


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root){ v, insets->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView(){
        //Inicializar el adapter pasando la lista y la accion del click

        val adapter = AuditAdapter(lista){ itemSeleccionado ->
            //Este lambda se ejecuta cuando doy clic a la tarjeta

            val intent = Intent(this, DetailActivity::class.java)

            intent.putExtra("EXTRA_ITEM",itemSeleccionado)
            startActivity(intent)

        }
        binding.rvAuditoria.adapter = adapter
        binding.rvAuditoria.layoutManager = LinearLayoutManager(this)
    }
}
