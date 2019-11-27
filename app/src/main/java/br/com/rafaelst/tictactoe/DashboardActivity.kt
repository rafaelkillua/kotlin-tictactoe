package br.com.rafaelst.tictactoe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    var playerId: Int? = 0
    var credits: Int? = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        player1text.setText(intent.getStringExtra("player"))
        credits = intent.getIntExtra("credits", 0)
        creditsText.text = "Voce tem $credits creditos"
        playerId = intent.getIntExtra("playerId", 0)
        if (intent.getIntExtra("credits", 0) < 1) {
            Toast.makeText(
                this,
                "Creditos expirados. Compre mais creditos para jogar!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun logoff(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun play(view: View) {
        val buttonClicked = view as Button

        when (buttonClicked.id) {
            R.id.btnJogar -> {
                if (credits!! > 0) {
                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("player1", player1text.text.toString())
                    intent.putExtra("player2", player2text.text.toString())
                    intent.putExtra("playerId", playerId)
                    intent.putExtra("credits", credits!!)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.btnHistory -> {
                val intent = Intent(this, HistoryActivity::class.java)
                intent.putExtra("player", player1text.text.toString())
                intent.putExtra("playerId", playerId)
                intent.putExtra("credits", credits!!)
                startActivity(intent)
                finish()
            }

            R.id.btnComprar -> {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("player", player1text.text.toString())
                intent.putExtra("playerId", playerId)
                intent.putExtra("credits", credits!!)
                startActivity(intent)
                finish()
            }
        }
    }
}
