package com.example.sqlitekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    private var stdList: ArrayList<UsuarioModel> = ArrayList()
    private var onClickItem: ((UsuarioModel) -> Unit)? = null
    private var onClickDeleteItem: ((UsuarioModel)-> Unit)? = null


    fun addItems(items:ArrayList<UsuarioModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOncClickItem (callback: (UsuarioModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeteleItem(callback: (UsuarioModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsuarioViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_std,parent,false)
    )

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
       val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(std)}

    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class UsuarioViewHolder (var view:View): RecyclerView.ViewHolder (view){
        private var user = view.findViewById<TextView>(R.id.tvUser)
        private var nombre = view.findViewById<TextView>(R.id.tvNombre)
        private var apellido = view.findViewById<TextView>(R.id.tvApellido)
        private var correo = view.findViewById<TextView>(R.id.tvCorreo)
        private var contrasena = view.findViewById<TextView>(R.id.tvContrasena)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bindView(std:UsuarioModel){
            nombre.text = std.nombre
            apellido.text = std.apellido
            user.text = std.user
            correo.text =std.correo
            contrasena.text=std.contrasena


        }
    }
}