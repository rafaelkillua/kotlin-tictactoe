package br.com.rafaelst.tictactoe

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    var player : String? = ""
    var playerId : Int? = 0
    var credits : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        player = intent.getStringExtra("player")
        playerId = intent.getIntExtra("playerId", 0)
        credits = intent.getIntExtra("credits", 0)

        try {
            val db = SqlHelper(this).writableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM $TBL_JOGO",
                null
            )

            while (cursor.moveToNext()) {
                val text = cursor.getString(cursor.getColumnIndex(TBL_JOGO_PLAYER1)) + " vs " +
                           cursor.getString(cursor.getColumnIndex(TBL_JOGO_PLAYER2)) + " => " +
                           cursor.getString(cursor.getColumnIndex(TBL_JOGO_WINNER)) + "\n"
                history.append(text)
            }

            cursor.close()
            db.close()
        } catch (e: SQLiteException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun back(view:View){
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("player", player)
        intent.putExtra("playerId", playerId)
        intent.putExtra("credits", credits)
        startActivity(intent)
        finish()
    }
}


