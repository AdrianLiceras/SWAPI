package com.example.ejemplointernet

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplointernet.databinding.ActivityMainBinding
import com.example.ejemplointernet.databinding.ItemPlanetBinding

class PlanetAdapter(var listaPlanet : MutableList<Planet>) : RecyclerView.Adapter<PlanetAdapter.TextoViewHolder>(){
    class TextoViewHolder(var itemBinding :ItemPlanetBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun getItemCount(): Int {
       return listaPlanet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetAdapter.TextoViewHolder {
        val binding=ItemPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemBinding.tvNombre.text=listaPlanet[position].name
        holder.itemBinding.itemPlanet.setOnClickListener{
            val intent = Intent(holder.itemBinding.root.context,DescargarPlanet::class.java)
            intent.putExtra("PLANETA",listaPlanet[position].url)
            startActivity(intent)

        }
    }



}