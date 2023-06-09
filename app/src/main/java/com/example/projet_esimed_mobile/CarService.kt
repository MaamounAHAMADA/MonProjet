package com.example.projet_esimed_mobile

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CarService {

    object Car{


        val timeoutSeconds = 30L // Définissez la durée d'attente en secondes
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .build()
        private val BASE_URL = "http://172.20.10.2:3000/"
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userAccountRoutes = retrofit.create(UserAccountRoutes::class.java)
    }

}