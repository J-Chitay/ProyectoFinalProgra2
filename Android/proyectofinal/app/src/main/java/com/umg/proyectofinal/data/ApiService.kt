package com.umg.proyectofinal.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val status: String, val token: String?, val nombre: String?, val role: String?)

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
