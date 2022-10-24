package com.example.cvbuilder.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    // Make into static read from any class without using instance
    companion object{
        // Define the base url
        val  base_url:String = "https://ged21c9a6c3b8b0-free1.adb.us-phoenix-1.oraclecloudapps.com/"
        // Get the client
        fun getClient(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
            val retrofit= Retrofit.Builder() // build your Retrofit Object
                .baseUrl(base_url) // hit the url
                .addConverterFactory(GsonConverterFactory.create()) // Perform serialization and deserialization
                .client(okHttpClient)
                .build() // Create an instance using the configured values
            return retrofit
        }
        fun apiInterface() : APIInterface { // returns an instance of your interface
            // Create an implementation of the API endpoints defined by the interface using client
            return  getClient().create(APIInterface::class.java) // pass the interface name to get the response
        }
    }
}