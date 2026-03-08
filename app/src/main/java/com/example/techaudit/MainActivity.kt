package com.example.techaudit

import android.app.Activity
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techaudit.adapter.AuditAdapter
import com.example.techaudit.data.AuditDatabase
import com.example.techaudit.databinding.ActivityMainBinding
import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.AuditStatus
import com.example.techaudit.ui.AuditViewModel
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: AuditAdapter

    private val viewModel: AuditViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        configurarDeslizarParaBorrar()

        viewModel.allItems.observe(this){ listaActualizada->
            adapter.actualizarLista(listaActualizada)

        }


        binding.fabAgregar.setOnClickListener {

            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
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

        val adapter = AuditAdapter(mutableListOf()){ itemSeleccionado ->
            //Este lambda se ejecuta cuando doy clic a la tarjeta

            val intent = Intent(this, AddEditActivity::class.java)

            intent.putExtra("EXTRA_ITEM_EDITAR",itemSeleccionado)
            startActivity(intent)

        }
        binding.rvAuditoria.adapter = adapter
        binding.rvAuditoria.layoutManager = LinearLayoutManager(this)
    }

    private fun configurarDeslizarParaBorrar() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(
            0, // No nos importa mover arriba/abajo
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Permitir deslizar a izq y der
        ) {
            override fun onMove(r: RecyclerView, v: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder): Boolean = false

            // Este método se dispara cuando el usuario suelta el dedo tras deslizar
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posicion = viewHolder.adapterPosition
                val itemABorrar = adapter.listaAuditoria[posicion]

                viewModel.delete(itemABorrar)
                Toast.makeText(this@MainActivity, "Equipo Eliminado", Toast.LENGTH_SHORT).show()

            }
        }

        // Conectamos este comportamiento a nuestra lista
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvAuditoria)
    }


}
