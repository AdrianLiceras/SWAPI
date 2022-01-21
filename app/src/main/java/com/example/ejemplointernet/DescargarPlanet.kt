package com.example.ejemplointernet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ejemplointernet.databinding.ActivityDescargarPlanetBinding
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class DescargarPlanet : AppCompatActivity() {
    private lateinit var binding :ActivityDescargarPlanetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescargarPlanetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.descargarPlanet.setOnClickListener {
            val planetaURL = intent.getStringExtra("PLANETA")
            val client = OkHttpClient()
            val request = Request.Builder()
            if (planetaURL!=null) {
                request.url(planetaURL)
            }

            val call = client.newCall(request.build())
            call.enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@DescargarPlanet, "Algo ha ido mal", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onResponse(call: Call, response: Response) {
                    println(response.toString())
                    response.body?.let { responseBody ->
                        val body = responseBody.string()
                        println(body)
                        val gson = Gson()

                        val planet = gson.fromJson(body, Planet::class.java)

                        println(planet)


                        CoroutineScope(Dispatchers.Main).launch {

                            Toast.makeText(this@DescargarPlanet, "$planet", Toast.LENGTH_SHORT).show()
                            if (planet!=null)
                                binding.tvDescarga.text=planet.toString()
                            else
                                binding.tvDescarga.visibility=View.GONE

                            if (planet.name!=null)
                                binding.tvName.text=planet.name
                            else
                                binding.tvName.visibility=View.GONE

                            if (planet.rotationPeriod!=null)

                                binding.tvRotationPeriod.text=planet.rotationPeriod
                            else
                                binding.tvRotationPeriod.visibility=View.GONE

                            if (planet.orbitalPeriod!=null)
                                binding.tvOrbitalPeriod.text=planet.orbitalPeriod
                            else
                                binding.tvOrbitalPeriod.visibility=View.GONE

                            if (planet.diameter!=null)
                                binding.tvDiameter.text=planet.diameter
                            else
                                binding.tvDiameter.visibility=View.GONE

                            if (planet.climate!=null)
                                binding.tvClimate.text=planet.diameter
                            else
                                binding.tvClimate.visibility=View.GONE

                            if (planet.gravity!=null)
                                binding.tvGravity.text=planet.diameter
                            else
                                binding.tvGravity.visibility=View.GONE

                            binding.tvTerrain.text=planet.terrain
                            binding.tvSurfaceWater.text=planet.surfaceWater
                            binding.tvPopulation.text=planet.population

                            if (planet.residents.isNotEmpty()) {
                                var residents: String = ""
                                planet.residents.forEach {
                                    residents += "$it "
                                }.toString()
                                binding.tvResidents.text = residents
                            }else
                                binding.tvResidents.visibility=View.GONE

                            if (planet.films.isNotEmpty()) {
                                var residents: String = ""
                                planet.films.forEach {
                                    residents += "$it \n"
                                }.toString()
                                binding.tvFilms.text = residents
                            }else
                                binding.tvFilms.visibility=View.GONE

                            binding.tvCreated.text=planet.created
                            binding.tvEdited.text=planet.edited
                            binding.tvUrl.text=planetaURL



                        }
                    }
                }
            })
        }
    }
}