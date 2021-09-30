package com.example.sqlitekotlin

import java.util.*


data class UsuarioModel (
    var idusuario : Int = getAutoId(),
    var nombre : String = "",
    var apellido: String = "",
    var correo: String = "",
    var user: String = "",
    var contrasena: String = ""

        ) {
    companion object {
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
    }

    }
}