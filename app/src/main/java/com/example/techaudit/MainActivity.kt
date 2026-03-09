package com.example.techaudit

import android.content.Intent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techaudit.adapter.LaboratorioAdapter
import com.example.techaudit.databinding.ActivityMainBinding
import com.example.techaudit.model.Laboratorio
import com.example.techaudit.ui.LaboratorioViewModel



class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: LaboratorioAdapter

    private val viewModel: LaboratorioViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ConfigurarRecycler()
        observarLaboratorios()
        configurarBotonAgregar()

        // BOTON SINCRONIZAR
        binding.btnSincronizar.setOnClickListener {
            viewModel.sincronizar()
            Toast.makeText(this,"Sincronizando...",Toast.LENGTH_SHORT).show()
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root){ v, insets->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun ConfigurarRecycler(){
        //Inicializar el adapter pasando la lista y la accion del click

        adapter = LaboratorioAdapter(mutableListOf()){ laboratorio ->

            abrirLaboratorio(laboratorio)
        }
        binding.rvAuditoria.adapter = adapter
        binding.rvAuditoria.layoutManager = LinearLayoutManager(this)
    }

    private fun observarLaboratorios(){
        viewModel.allLaboratorios.observe(this){ lista ->
            adapter.actualizarLista(lista)
        }
    }

    private fun configurarBotonAgregar(){
        binding.fabAgregar.setOnClickListener {
            val intent = Intent(this, AddLaboratorioActivity::class.java)
            startActivity(intent)
        }
    }

    private fun abrirLaboratorio(lab: Laboratorio){
        val intent = Intent(this, EquiposLaboratorioActivity::class.java)

        intent.putExtra("LAB_ID",lab.id)
        intent.putExtra("LAB_NOMBRE",lab.nombre)

        startActivity(intent)
    }

}
