package br.com.rafaelst.tictactoe

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            val credits: Int?
            val id: Int?
            val username: String? = inputUsername.text.toString()
            val password: String? = inputPassword.text.toString()

            //DBCON
            val bd = SqlHelper(this).writableDatabase
            //STATEMENT
            val sql =
                "SELECT * FROM $TBL_USUARIO JOIN $TBL_USUARIO_JOGO ON $TBL_USUARIO.$TBL_USUARIO_ID = $TBL_USUARIO_JOGO.$TBL_USUARIO_IDU" +
                        " WHERE $TBL_USUARIO.$TBL_USUARIO_LOGIN = ? AND" +
                        " $TBL_USUARIO.$TBL_USUARIO_SENHA = ?"
            //RESULTSET
            val cursor = bd.rawQuery(sql, arrayOf(username, password))

            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndex(TBL_USUARIO_ID))
                credits = cursor.getInt(cursor.getColumnIndex(TBL_USUARIO_JOGO_COUNT))

                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("player", cursor.getString(cursor.getColumnIndex(TBL_USUARIO_LOGIN)))
                intent.putExtra("playerId", id)
                intent.putExtra("credits", credits)
                cursor.close()
                bd.close()

                startActivity(intent)

                finish()
            } else {
                if (username == "" || password == "") {
                    Toast.makeText(
                        this,
                        "Ha algum campo invalido",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val contentValues = ContentValues().apply {
                        put(TBL_USUARIO_LOGIN, username)
                        put(TBL_USUARIO_SENHA, password)
                    }

                    val newUserId = bd.insert(
                        TBL_USUARIO, null, contentValues
                    )
                    val contentValues2 = ContentValues().apply {
                        put(TBL_USUARIO_IDU, newUserId)
                        put(TBL_USUARIO_JOGO_COUNT, 3)

                    }
                    bd.insert(
                        TBL_USUARIO_JOGO, null, contentValues2
                    )

                    if (newUserId != -1L) {
                        Toast.makeText(
                            this,
                            "Usuario cadastrado com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                        btnLogin.performClick()
                    } else {
                        Toast.makeText(
                            this,
                            "Erro ao cadastrar usuario.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    bd.close()
                }
            }
        }
    }
}


