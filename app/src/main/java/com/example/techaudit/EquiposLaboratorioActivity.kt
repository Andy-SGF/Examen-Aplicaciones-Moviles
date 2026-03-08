package com.example.techaudit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techaudit.adapter.AuditAdapter
import com.example.techaudit.databinding.ActivityEquiposLaboratorioBinding
import com.example.techaudit.ui.AuditViewModel

class EquiposLaboratorioActivity : ComponentActivity() {

    private lateinit var binding: ActivityEquiposLaboratorioBinding
    private lateinit var adapter: AuditAdapter

    private val viewModel: AuditViewModel by viewModels()

    private var laboratorioId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEquiposLaboratorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recibir datos del laboratorio
        laboratorioId = intent.getIntExtra("LAB_ID",-1)
        val nombreLaboratorio = intent.getStringExtra("LAB_NOMBRE")

        //Mostrar el nombre del laboratorio en el titulo
        binding.tvTituloLaboratorio.text = nombreLaboratorio

        setupRecyclerView()
        configurarDeslizarParaBorrar()

        //observar equipos del laboratorio
        viewModel.getEquiposByLaboratorio(laboratorioId).observe(this){ listaActualizada ->
            adapter.actualizarLista(listaActualizada)

        }

        binding.fabAgregarEquipo.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("LAB_ID",laboratorioId)
            startActivity(intent)
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView(){

        adapter = AuditAdapter(mutableListOf()){ itemSeleccionado ->

            val intent = Intent(this, AddEditActivity::class.java)

            intent.putExtra("EXTRA_ITEM_EDITAR", itemSeleccionado)

            startActivity(intent)
        }

        binding.rvEquipos.adapter = adapter
        binding.rvEquipos.layoutManager = LinearLayoutManager(this)
    }

    private fun configurarDeslizarParaBorrar(){

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val posicion = viewHolder.adapterPosition
                val itemABorrar = adapter.listaAuditoria[posicion]

                viewModel.delete(itemABorrar)

                Toast.makeText(
                    this@EquiposLaboratorioActivity,
                    "Equipo eliminado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvEquipos)
    }

}