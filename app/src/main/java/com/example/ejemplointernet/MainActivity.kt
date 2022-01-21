package com.example.ejemplointernet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplointernet.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var  adapter:PlanetAdapter
    lateinit var  listaPlanet:MutableList<Planet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list= List(1){""}
        val planetaaa=Planet("","","","","","","","","",list,list,"","","")
         listaPlanet= MutableList<Planet>(0){planetaaa}


            val client = OkHttpClient()

            val request = Request.Builder()
            request.url("https://swapi.dev/api/planets/")


            val call = client.newCall(request.build())

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@MainActivity, "Algo ha ido mal", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onResponse(call: Call, response: Response) {
                    println(response.toString())
                    response.body?.let { responseBody ->

                        val body = responseBody.string()
                        println(body)
                        val gson = Gson()

                        val planet = gson.fromJson(body, PlanetResponse::class.java)

                        println(planet)


                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(this@MainActivity, "$planet", Toast.LENGTH_SHORT).show()
                            //listaPlanet = MutableList(60) {planet.results[it]}
                            planet.results.forEach {
                                listaPlanet.add(it)
                            }
                            adapter=PlanetAdapter(listaPlanet)

                            binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                            binding.recyclerview.adapter = adapter

                            listaPlanet.forEach {
                                println(it.name)
                            }






                        }


                    }

                }

            })
        //Funcion en ActivityMain
        fun pasarPantalla(planet: String){
            val intent = Intent(this, DescargarPlanet::class.java)
            intent.putExtra("PLANETA", planet)
            startActivity(intent)
        }

        }



    }






/* binding.bDescargaUno.setOnClickListener {
            binding.pbDownloading.visibility = View.VISIBLE

            val client = OkHttpClient()

            val request = Request.Builder()
            request.url("https://swapi.dev/api/planets/1/")


            val call = client.newCall(request.build())
            call.enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@MainActivity, "Algo ha ido mal", Toast.LENGTH_SHORT).show()
                        binding.pbDownloading.visibility = View.GONE
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
                            binding.pbDownloading.visibility = View.GONE
                            Toast.makeText(this@MainActivity, "$planet", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }*/
