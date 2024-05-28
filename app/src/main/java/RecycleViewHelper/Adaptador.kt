package RecycleViewHelper

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import fernanda.hernandez.crudfernanda.R
import fernanda.hernandez.crudfernanda.detallemascota
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.Claseconexion
import modelo.DataClassmascotas
import java.util.UUID


class Adaptador(private var Datos: List<DataClassmascotas>) : RecyclerView.Adapter<ViewHolder>() {

    fun Actualizarlista(nuevoList: List<DataClassmascotas>) {
        Datos = nuevoList
        notifyDataSetChanged()  //notifica al recycle view  que hay datos nuevos


    }

    fun actualizarpantalla (uuid: String, nuevonombre: String){
        val index =Datos.indexOfFirst{it.uuid == uuid}
        notifyDataSetChanged()
    }

    fun eliminardatos(nombreMascota: String, position: Int) {

        //actualizar lista
        val listadatos = Datos.toMutableList()
        listadatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {

            //objeto clase conexion
            val objconexion = Claseconexion().cadenaConexion()

            //crear una variable que contenga un preparestatement = manda la info a sql
            val deletemascota =
                objconexion?.prepareStatement("delete from mascotasfernanda where nombremascota = ? ")!!
            deletemascota.setString(1, nombreMascota)
            deletemascota.executeUpdate()

            val commit = objconexion?.prepareStatement("commit")!!
            commit.executeUpdate()


        }
        Datos = listadatos.toList()
        //notificar que los datos se cambiaron
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    //actualizar datos
    fun actualizardatos(nuevonombre: String, uuid: String){
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = Claseconexion().cadenaConexion()

            //VAIRABLE QUE TENGA UN PREPARESTATEMENT
            val updatemascotas = objConexion?.prepareStatement("update mascotasfernanda set nombremascota = ? where uuid = ?")!!
            updatemascotas.setString(1, nuevonombre)
            updatemascotas.setString(2, uuid)
            updatemascotas.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarpantalla(uuid, nuevonombre)
            }
        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_itemcard, parent, false)

        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size //devuelve los datos

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mascota = Datos[position]
        holder.textView.text = mascota.nombremascotas

        holder.imgborrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea eliminar la mascota?")

            //botones
            builder.setPositiveButton("si") { dialog, which ->
                eliminardatos(mascota.nombremascotas, position)


            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        //alerta para editar
        holder.imgeditar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar")
            builder.setMessage("¿Desea editar la mascota?")

            //cuadro de texto para editar
            val cuadrotexto = EditText(context)
            cuadrotexto.setHint(mascota.nombremascotas)
            builder.setView(cuadrotexto)

            //botones
            builder.setPositiveButton("si") { dialog, which ->
                actualizardatos(cuadrotexto.text.toString(), mascota.uuid)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        //otra pantalla
        holder.itemView.setOnClickListener(){
            val context = holder.itemView.context

            //cambiar pantalla
            val pantalladetalle = Intent(context, detallemascota::class.java)

            pantalladetalle.putExtra("mascotaUUID", mascota.uuid)
            pantalladetalle.putExtra("nombremascota", mascota.nombremascotas)
            pantalladetalle.putExtra("edad", mascota.edad)
            pantalladetalle.putExtra("peso", mascota.peso)

            context.startActivity(pantalladetalle)
        }


  }
}
