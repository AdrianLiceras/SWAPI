package com.example.ejemplointernet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                        }
                    }
                }
            })
        }
    }
}