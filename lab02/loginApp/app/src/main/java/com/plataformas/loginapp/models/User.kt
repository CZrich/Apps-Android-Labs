package com.plataformas.loginapp.models

import org.json.JSONObject

class User {

     val nombres:String
     val apellidos:String
     val correo: String
     val telefo: String
     val usuario: String
     val password:String

     constructor(
         nombres: String,
         apellidos: String,
         correo: String,
         telefo: String,
         usuario: String,
         password: String
     ) {
         this.nombres = nombres
         this.apellidos = apellidos
         this.correo = correo
         this.telefo = telefo
         this.usuario = usuario
         this.password = password
     }
     fun toJson(): String {
         val json = JSONObject()
         json.put("nombres", nombres)
         json.put("apellidos", apellidos)
         json.put("correo", correo)
         json.put("telefo", telefo)
         json.put("usuario", usuario)
         json.put("password", password)
         return json.toString()
     }

     companion object {
         fun fromJson(jsonStr: String): User {
             val obj = JSONObject(jsonStr)
             return User(
                 obj.getString("nombres"),
                 obj.getString("apellidos"),
                 obj.getString("correo"),
                 obj.getString("telefo"),
                 obj.getString("usuario"),
                 obj.getString("password")
             )
         }
     }


 }