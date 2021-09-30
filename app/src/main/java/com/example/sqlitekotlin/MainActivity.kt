package com.example.sqlitekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edNombre : EditText
    private lateinit var edApellido: EditText
    private lateinit var edCorreo: EditText
    private lateinit var edUser: EditText
    private lateinit var edContrasena: EditText
    private lateinit var btnAdd:Button
    private lateinit var btnView:Button
    private lateinit var btnUpdate:Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : UsuarioAdapter? = null
    private var std : UsuarioModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{addUsuario()}
        btnView.setOnClickListener {getUsuario()}
        btnUpdate.setOnClickListener{updateUsuario()}

        adapter?.setOncClickItem {
            Toast.makeText(this, it.nombre, Toast.LENGTH_SHORT).show()
            edUser.setText(it.user)
            edNombre.setText(it.nombre)
            edApellido.setText(it.apellido)
            edCorreo.setText(it.correo)
            edContrasena.setText(it.contrasena)
            std = it
        }

        adapter?.setOnClickDeteleItem {
            deleteUsuario(it.idusuario)

        }
    }


    private fun getUsuario() {
        val stdList = sqLiteHelper.getAllUsuario()
        Log.e("ppppp", "${stdList.size}")

        adapter?.addItems(stdList)
    }

    private  fun addUsuario(){
        val nombre = edNombre.text.toString()
        val apellido = edApellido.text.toString()
        val correo = edCorreo.text.toString()
        val user = edUser.text.toString()
        val contrasena = edContrasena.text.toString()

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || user.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el dato requerido", Toast.LENGTH_SHORT).show()
        }else{
            val std = UsuarioModel(nombre = nombre, apellido = apellido, correo = correo, user = user,contrasena = contrasena)
            val status = sqLiteHelper.insertUsuario(std)

            if (status > -1){
                Toast.makeText(this, "Usuario agregado..", Toast.LENGTH_SHORT).show()
                clearEditText()
                getUsuario()
            }else{
                Toast.makeText(this, "Datos no guardados", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updateUsuario() {
        val user = edUser.text.toString()
        val nombre = edNombre.text.toString()
        val apellido = edApellido.text.toString()
        val correo = edCorreo.text.toString()
        val contrasena = edContrasena.text.toString()

        if (user == std?.user && nombre == std?.nombre && apellido == std?.apellido && correo == std?.correo && contrasena == std?.contrasena){
            Toast.makeText(this, "No hubo cambios", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = UsuarioModel(idusuario = std!!.idusuario, user = user, nombre = nombre, apellido = apellido, correo = correo,contrasena = contrasena)
        val status = sqLiteHelper.updateUsuario(std)
        if (status > -1){
            clearEditText()
            getUsuario()
        }else{
            Toast.makeText(this, "Cambio Fallido", Toast.LENGTH_SHORT).show()
        }
    }
    private fun deleteUsuario(idusuario:Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Esta seguro de borrar este usuario?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _ ->
            sqLiteHelper.deleteUsuario(idusuario)
            getUsuario()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText(){
        edNombre.setText("")
        edApellido.setText("")
        edCorreo.setText("")
        edUser.setText("")
        edContrasena.setText("")
        edNombre.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UsuarioAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView(){
        edNombre = findViewById(R.id.ednombre)
        edApellido = findViewById(R.id.edapellido)
        edCorreo= findViewById(R.id.edcorreo)
        edUser = findViewById(R.id.eduser)
        edContrasena = findViewById(R.id.edcontrasena)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}