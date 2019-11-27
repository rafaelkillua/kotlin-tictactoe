package br.com.rafaelst.tictactoe

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_payment.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class PaymentActivity : AppCompatActivity() {

    var player : String? = ""
    var playerId : Int? = 0
    var credits : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        player = intent.getStringExtra("player")
        playerId = intent.getIntExtra("playerId", 0)
        credits = intent.getIntExtra("credits", 0)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val spinner: Spinner = findViewById(R.id.bandeira_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.bandeiras_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun pagar (view: View) {
        val spinner: Spinner = findViewById(R.id.bandeira_spinner)
        val bandeira = spinner.selectedItem.toString().toLowerCase()
        val valor_em_centavos = 499
        val parcelas = 1
        val numero_cartao: String? = numero_cartao.text.toString()
        val nome_cliente: String? = nome_cliente.text.toString()
        val cod_seguranca: String? = cod_seguranca.text.toString()

        var reqParam = URLEncoder.encode("numero_cartao", "UTF-8") + "=" + URLEncoder.encode(numero_cartao, "UTF-8")
        reqParam += "&" + URLEncoder.encode("nome_cliente", "UTF-8") + "=" + URLEncoder.encode(nome_cliente?.toUpperCase(), "UTF-8")
        reqParam += "&" + URLEncoder.encode("cod_seguranca", "UTF-8") + "=" + URLEncoder.encode(cod_seguranca, "UTF-8")
        reqParam += "&" + URLEncoder.encode("parcelas", "UTF-8") + "=" + URLEncoder.encode(parcelas.toString(), "UTF-8")
        reqParam += "&" + URLEncoder.encode("valor_em_centavos", "UTF-8") + "=" + URLEncoder.encode(valor_em_centavos.toString(), "UTF-8")
        reqParam += "&" + URLEncoder.encode("bandeira", "UTF-8") + "=" + URLEncoder.encode(bandeira, "UTF-8")
        val mURL = URL("https://pdsiii-operators.herokuapp.com/ws-banks/v1/pay")

        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "POST"

            val wr = OutputStreamWriter(outputStream)
            wr.write(reqParam)
            wr.flush()

            if (responseCode < 400) {
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    addCredits()
                    Toast.makeText(this@PaymentActivity, "Pagamento realizado com sucesso!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@PaymentActivity, "Erro ao processar pagamento", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addCredits() {
        val bd = SqlHelper(this).writableDatabase
        val newCredits = credits?.plus(3)
        credits = newCredits
        val contentValues = ContentValues().apply {
            put(TBL_USUARIO_JOGO_COUNT, newCredits)
        }
        bd.update(
            TBL_USUARIO_JOGO, contentValues, "$TBL_USUARIO_IDU = ?", arrayOf(playerId.toString())
        )
        bd.close()
    }

    fun tchau (view: View) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("player", player)
        intent.putExtra("playerId", playerId)
        intent.putExtra("credits", credits)

        startActivity(intent)
        finish()
    }
}


