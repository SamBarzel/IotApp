package mx.tecnm.cdhidalgo.iotapp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest

class MainActivity : AppCompatActivity() {
    //elementos de la vista
    lateinit var etLoginUser: EditText
    lateinit var etLoginPass: EditText
    lateinit var btnLoginStart: Button
    lateinit var sesion: SharedPreferences 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etLoginUser = findViewById(R.id.etLoginUser)
        etLoginPass = findViewById(R.id.etLoginPass)
        btnLoginStart = findViewById(R.id.btnLoginStart)

        sesion = getSharedPreferences("sesion", 0)
        btnLoginStart.setOnClickListener { login() }
    }

    private fun login() {
        val url = Uri.parse(Config.URL + "login")
            .buildUpon()
            .build().toString()

        val peticion = object: StringRequest(Request.Method.POST, url, {
            response -> with(sesion.edit()) {
                putString("jwt", response) //clave, valor   y guarda el token
                putString("username", etLoginUser.text.toString())
                apply()
        }
            startActivity(Intent(this, MainActivity2::class.java))
        }, {
           error ->

            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()

            Log.e("LOGIN", error.stackTraceToString())
        }) {
            override fun getParams(): Map<String, String>{
              val body: MutableMap<String, String> = HashMap()
              body["username"] = etLoginUser.text.toString()
              body.put("password", etLoginPass.text.toString())
              return body
            }
        }
        MySingleton.getInstance(applicationContext).addToRequestQueue(peticion)

    }
}