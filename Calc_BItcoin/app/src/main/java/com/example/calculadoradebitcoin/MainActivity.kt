package com.example.calculadoradebitcoin

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat


class MainActivity : ComponentActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"

    var cotacaoBitcoin: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_calcular = findViewById<Button>(R.id.btn_calcular)


        buscarCotacao()

        btn_calcular.setOnClickListener {
            calcular()
        }
    }

    // adicionando a dependencia do AlertDialog pós não temos a variavel alert() pós faz parte da lib do anko
    fun Context.materialAlert(
        message: String,
        title: String? = null
    ): AlertDialog.Builder {
        return AlertDialog.Builder(this).apply {
            if (title != null) setTitle(title)
            setMessage(message)
        }
    }


    // fazendo requisição de busca a api(GET)
    fun buscarCotacao() {
        // usando a biblioteca coroutines pós a anko foi descontinuada
        lifecycleScope.launch {
            // codigo em background mesma função do metodo doASync
            val resposta = withContext(Dispatchers.IO) {
                URL(API_URL).readText()
            }
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")

            val f = NumberFormat.getCurrencyInstance(java.util.Locale("pt", "br"))

            val cotacaoFormatada = f.format(cotacaoBitcoin)
            val txt_cotacao = findViewById<TextView>(R.id.txt_cotacao)


            // fazendo o alert para ver retorno da api
            materialAlert(txt_cotacao.setText("$cotacaoFormatada").toString())

        }
    }

    fun calcular() {

        val txt_valor = findViewById<EditText>(R.id.txt_valor)
        val txt_qtd_bitcoins = findViewById<TextView>(R.id.txt_qtd_bitcoins)

        if (txt_valor.text.isEmpty()) {
            txt_valor.error = "Preencha um valor"
            return
        }

        val valor_digitado = txt_valor.text.toString().replace(",",".").toDouble()

        val resultado = if (cotacaoBitcoin > 0 ) valor_digitado / cotacaoBitcoin else 0.0

        txt_qtd_bitcoins.text = "%.8f".format(resultado)

    }
}