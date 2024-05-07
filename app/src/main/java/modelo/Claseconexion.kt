package modelo

import java.sql.Connection
import java.sql.DriverManager

class Claseconexion {
    fun cadenaConexion(): Connection? {
        try {
            val ip = "jdbc:oracle:thin:@10.10.1.168:1521:xe"
            val usuario = "system"
            val contraseña = "desarrollo"
            val connection = DriverManager.getConnection(ip, usuario, contraseña)

            return connection


        } catch (e:Exception){
            println("este es el error: $e")
            return null
        }
    }
}