package br.com.rafaelst.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val STOPPED = 0
    val RUNNING = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var gameRunning = RUNNING
    var currentPlayer = 1
    val player1 = ArrayList<Int>()
    val player2 = ArrayList<Int>()
    var player1victories = 0
    var player2victories = 0

    fun clickButton(view: View) {
        val buttonClicked = view as Button
        var id = -1

        when (buttonClicked.id) {
            R.id.button_1 -> id = 1
            R.id.button_2 -> id = 2
            R.id.button_3 -> id = 3
            R.id.button_4 -> id = 4
            R.id.button_5 -> id = 5
            R.id.button_6 -> id = 6
            R.id.button_7 -> id = 7
            R.id.button_8 -> id = 8
            R.id.button_9 -> id = 9
        }

        if (gameRunning == RUNNING) {
            if (!player1.contains(id) && !player2.contains(id)) {
                if (currentPlayer == 1) {
                    buttonClicked.text = "X"
                    buttonClicked.setBackgroundColor(Color.parseColor("#FF03F4E0"))
                    buttonClicked.setTextColor(Color.parseColor("#f00000"))
                    player1.add(id)
                    currentPlayer = 2
                } else {
                    buttonClicked.text = "O"
                    buttonClicked.setBackgroundColor(Color.parseColor("#FFECF373"))
                    buttonClicked.setTextColor(Color.parseColor("#010f7a"))
                    player2.add(id)
                    currentPlayer = 1
                }
                checkWinner()
            }
        } else {
            Toast.makeText(this, "Jogo terminado", Toast.LENGTH_LONG).show()
        }
    }

    fun checkWinner() {
        var winner = -1
        val possibleWinningValues = arrayListOf(
            arrayListOf(1, 2, 3),
            arrayListOf(4, 5, 6),
            arrayListOf(7, 8, 9),
            arrayListOf(1, 4, 7),
            arrayListOf(2, 5, 8),
            arrayListOf(3, 6, 9),
            arrayListOf(1, 5, 9),
            arrayListOf(3, 5, 7)
        )

        for (array in possibleWinningValues) {
            if (player1.containsAll(array)) {
                winner = 1
            }
            if (player2.containsAll(array)) {
                winner = 2
            }
        }

        if (winner != -1) {
            gameRunning = STOPPED
            restart_button.visibility = View.VISIBLE
            if (winner == 1) {
                player1victories += 1
                player1score.text = player1victories.toString()
            } else if (winner == 2) {
                player2victories += 1
                player2score.text = player2victories.toString()
            }
            Toast.makeText(this, "Vitoria do player " + winner, Toast.LENGTH_LONG).show()
        } else {
            if (player1.size + player2.size == 9) {
                gameRunning = STOPPED
                restart_button.visibility = View.VISIBLE
                Toast.makeText(this, "Deu velha!", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun restartGame(view: View) {
        button_1.text = ""
        button_1.setBackgroundResource(android.R.drawable.btn_default)
        button_2.text = ""
        button_2.setBackgroundResource(android.R.drawable.btn_default)
        button_3.text = ""
        button_3.setBackgroundResource(android.R.drawable.btn_default)
        button_4.text = ""
        button_4.setBackgroundResource(android.R.drawable.btn_default)
        button_5.text = ""
        button_5.setBackgroundResource(android.R.drawable.btn_default)
        button_6.text = ""
        button_6.setBackgroundResource(android.R.drawable.btn_default)
        button_7.text = ""
        button_7.setBackgroundResource(android.R.drawable.btn_default)
        button_8.text = ""
        button_8.setBackgroundResource(android.R.drawable.btn_default)
        button_9.text = ""
        button_9.setBackgroundResource(android.R.drawable.btn_default)
        restart_button.visibility = View.INVISIBLE
        gameRunning = RUNNING
        currentPlayer = 1
        player1.clear()
        player2.clear()
    }
}
