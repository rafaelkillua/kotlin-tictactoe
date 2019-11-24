package br.com.rafaelst.tictactoe

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper(context: Context) : SQLiteOpenHelper(context, BD_NOME, null, BD_VERSAO) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE $TBL_USUARIO (" +
                    "$TBL_USUARIO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TBL_USUARIO_LOGIN TEXT NOT NULL UNIQUE, " +
                    "$TBL_USUARIO_SENHA TEXT NOT NULL)"
        )
        sqLiteDatabase.execSQL(
            "CREATE TABLE $TBL_USUARIO_JOGO (" +
                    "$TBL_USUARIO_JOGO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TBL_USUARIO_IDU INTEGER NOT NULL, " +
                    "$TBL_USUARIO_JOGO_COUNT INTEGER DEFAULT 3, FOREIGN KEY($TBL_USUARIO_IDU) REFERENCES $TBL_USUARIO($TBL_USUARIO_ID) )"
        )
        sqLiteDatabase.execSQL(
            "CREATE TABLE $TBL_JOGO (" +
                    "$TBL_JOGO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TBL_JOGO_PLAYER1 TEXT NOT NULL, " +
                    "$TBL_JOGO_PLAYER2 TEXT NOT NULL, " +
                    "$TBL_JOGO_WINNER TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
