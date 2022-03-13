package com.example.android.webservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pesquisaCEP: Button = findViewById(R.id.pesquisaCEP)
        val cep: EditText = findViewById(R.id.cep)
        val progress_bar: ProgressBar = findViewById(R.id.progress_bar)

        //-- Ao clicar no botão número 1
        //-- Será pesquisado o logradouro com o número do CEP
        pesquisaCEP.setOnClickListener {

            progress_bar.visibility = View.VISIBLE

            val call = RetrofitFactory().retrofitService().getCEP(cep.text.toString())

            call.enqueue(object : Callback<CEP> {

                override fun onResponse(call: Call<CEP>, response: Response<CEP>) {

                    response.body()?.let {
                        Log.i("CEP", it.toString())
                        Toast.makeText(this@MainActivity, "O endereço " + it.toString() + " foi inserido com sucesso", Toast.LENGTH_LONG).show()
                        progress_bar.visibility = View.INVISIBLE
                    } ?: Toast.makeText(this@MainActivity, "CEP não localizado", Toast.LENGTH_LONG)
                        .show()

                }

                override fun onFailure(call: Call<CEP>?, t: Throwable?) {
                    t?.message?.let { it1 -> Log.e("Erro", it1) }
                    progress_bar.visibility = View.INVISIBLE
                }
            })
        }
    }
}