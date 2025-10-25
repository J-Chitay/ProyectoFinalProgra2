package com.umg.proyectofinal.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.umg.proyectofinal.R
import com.umg.proyectofinal.data.LoginRequest
import com.umg.proyectofinal.data.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            val request = LoginRequest(email, password)

            RetrofitClient.instance.login(request).enqueue(object : Callback<com.umg.proyectofinal.data.LoginResponse> {
                override fun onResponse(
                    call: Call<com.umg.proyectofinal.data.LoginResponse>,
                    response: Response<com.umg.proyectofinal.data.LoginResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == "success") {
                        Toast.makeText(this@MainActivity, "Bienvenido ${response.body()?.nombre}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<com.umg.proyectofinal.data.LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
