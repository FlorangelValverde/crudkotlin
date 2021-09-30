package com.example.sqlitekotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "usuario.db"
        private const val USUARIO = "usuario"
        private const val IDUSUARIO = "idusuario"
        private const val NOMBRE = "nombre"
        private const val APELLIDO = "apellido"
        private const val CORREO = "correo"
        private const val USER = "user"
        private const val CONTRASENA = "contrasena"

    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTblUsuario = (" CREATE TABLE " + USUARIO + "("
               + IDUSUARIO + " INTEGER PRIMARY KEY," + NOMBRE + " TEXT,"
               + APELLIDO + " TEXT," + CORREO + " TEXT,"
               + USER + " TEXT," + CONTRASENA + " TEXT" + ")")
        db?.execSQL(createTblUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $USUARIO")
        onCreate(db)
    }

    fun insertUsuario(std: UsuarioModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDUSUARIO, std.idusuario)
        contentValues.put(NOMBRE, std.nombre)
        contentValues.put(APELLIDO, std.apellido)
        contentValues.put(CORREO, std.correo)
        contentValues.put(USER, std.user)
        contentValues.put(CONTRASENA, std.contrasena)

        val success = db.insert(USUARIO, null, contentValues)
        db.close()
        return success
    }

    fun getAllUsuario(): ArrayList<UsuarioModel> {
        val stdList: ArrayList<UsuarioModel> = ArrayList()
        val selectQuery = "SELECT * FROM $USUARIO"
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var idusuario: Int
        var nombre: String
        var apellido: String
        var correo: String
        var user: String
        var contrasena: String

        if (cursor.moveToFirst()) {
            do {
                idusuario = cursor.getInt(cursor.getColumnIndex("idusuario"))
                nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                correo = cursor.getString(cursor.getColumnIndex("correo"))
                user = cursor.getString(cursor.getColumnIndex("user"))
                contrasena = cursor.getString(cursor.getColumnIndex("contrasena"))
                val std = UsuarioModel(
                    idusuario = idusuario,
                    nombre = nombre,
                    apellido = apellido,
                    correo = correo,
                    user = user,
                    contrasena = contrasena)
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        return stdList
    }

    fun updateUsuario(std: UsuarioModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(IDUSUARIO,std.idusuario)
        contentValues.put(USER, std.user)
        contentValues.put(NOMBRE, std.nombre)
        contentValues.put(APELLIDO, std.apellido)
        contentValues.put(CORREO, std.correo)
        contentValues.put(CONTRASENA, std.contrasena)

        val success = db.update(USUARIO, contentValues,"idusuario" + std.idusuario, null)
        db.close()
        return success

    }

    fun deleteUsuario(idusuario:Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IDUSUARIO, idusuario)

        val success = db.delete(USUARIO, "idusuario = $idusuario", null)
        db.close()
        return success
    }
}