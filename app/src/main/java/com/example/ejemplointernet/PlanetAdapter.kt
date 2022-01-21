package com.example.ejemplointernet


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplointernet.databinding.ItemPlanetBinding

class PlanetAdapter(var listaPlanet : MutableList<Planet>) : RecyclerView.Adapter<PlanetAdapter.TextoViewHolder>(){
    lateinit var planeta:String
    class TextoViewHolder(var itemBinding :ItemPlanetBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun getItemCount(): Int {
       return listaPlanet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):TextoViewHolder {
        val binding=ItemPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {

        holder.itemBinding.tvNombre.text=listaPlanet[position].name
        holder.itemBinding.itemPlanet.setOnClickListener{
            //Toast.makeText(holder.itemBinding.root.context, listaPlanet[position].toString(), Toast.LENGTH_SHORT).show()
            planeta=listaPlanet[position].url
            val intent = Intent(holder.itemBinding.root.context, DescargarPlanet::class.java)
            intent.putExtra("PLANETA", planeta)
            holder.itemBinding.root.context.startActivity(intent)
        }

    }

}