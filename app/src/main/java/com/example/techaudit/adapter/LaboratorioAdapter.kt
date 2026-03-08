package com.example.techaudit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techaudit.databinding.ItemLaboratorioBinding
import com.example.techaudit.model.Laboratorio

class LaboratorioAdapter (

    val listaLaboratorios: MutableList<Laboratorio>,
    private val onItemSelected: (Laboratorio) -> Unit
) : RecyclerView.Adapter<LaboratorioAdapter.LaboratorioViewHolder>(){

    inner class LaboratorioViewHolder(val binding: ItemLaboratorioBinding):
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaboratorioViewHolder{

        val binding = ItemLaboratorioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LaboratorioViewHolder(binding)
    }

    override fun getItemCount(): Int = listaLaboratorios.size

    fun actualizarLista(nuevaLista: List<Laboratorio>){

        listaLaboratorios.clear()
        listaLaboratorios.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: LaboratorioViewHolder, position: Int){

        val laboratorio = listaLaboratorios[position]

        holder.binding.tvNombreLaboratorio.text = laboratorio.nombre
        holder.binding.tvEdificio.text = laboratorio.edificio

        holder.itemView.setOnClickListener {
            onItemSelected(laboratorio)
        }
    }

}