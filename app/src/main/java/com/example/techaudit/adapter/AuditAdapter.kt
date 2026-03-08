package com.example.techaudit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techaudit.databinding.ItemAuditBinding

import com.example.techaudit.model.AuditStatus
import com.example.techaudit.model.AuditItem


//Este adapter nos permite comunicar nuestro modelo con la vista

class AuditAdapter (

    val listaAuditoria: MutableList<AuditItem>, //Listas de auditoria
    private val onItemSelected: (AuditItem) -> Unit //Funcion lambda para selecionar elementos
): RecyclerView.Adapter<AuditAdapter.AuditViewHolder>() { //Hereda de RecyclerView.Adapter

    inner class AuditViewHolder(val binding: ItemAuditBinding):
            RecyclerView.ViewHolder(binding.root)

    //1. Crear el molde (solo ocurre pocas veces)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuditViewHolder {
        val binding = ItemAuditBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return AuditViewHolder(binding)
    }

    //2.CONTEO (cuantos datos tengo?)
    override fun getItemCount(): Int = listaAuditoria.size

    fun actualizarLista(nuevaLista: List<AuditItem>){

        listaAuditoria.clear()
        listaAuditoria.addAll(nuevaLista)
        notifyDataSetChanged() //Refresca la pantalla
    }

    //3. PINTAR DATOS (Ocurre muchas veces cada vez que se hace scroll)
    override fun onBindViewHolder(holder: AuditViewHolder, position: Int) {

        val item = listaAuditoria[position]

        //Asignar textos
        holder.binding.tvNombreEquipo.text = item.nombre
        holder.binding.tvUbicacion.text = item.ubicacion
        holder.binding.tvEstadoLabel.text = item.estado.name

        //Lógica visual: Cambiar colores segun Enum

        val colorEstado = when(item.estado){
            AuditStatus.PENDIENTE -> Color.parseColor("#4CAF50")
            AuditStatus.OPERATIVO -> Color.parseColor("#9E9E9E")
            AuditStatus.DANIADO -> Color.parseColor("#F44336")
            AuditStatus.NO_ENCONTRADO -> Color.BLACK
        }

        //Pintar la barra lateral y el texto
        holder.binding.viewStatusColor.setBackgroundColor(colorEstado)
        holder.binding.tvEstadoLabel.setTextColor(colorEstado)

        //Configurar el click en toda la tarjeta

        holder.itemView.setOnClickListener{
            onItemSelected(item)  //Devuelve el objeto seleccionado al Activity
        }

    }


}