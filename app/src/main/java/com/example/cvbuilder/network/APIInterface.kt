package com.example.cvbuilder.network

import com.example.cvbuilder.db.CV
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    //@GET("/ords/ejemplos/hr/employees/")
    //fun  getEmployees(): Call<Employees>
    /*Call -->  An invocation of a Retrofit method that sends a request to a webserver and returns a response.
     Each call yields its own HTTP request and response pair.*/
    @POST("/ords/mdp/cv/cv")
    fun postCV(@Body cv:CV):Call<String>

}