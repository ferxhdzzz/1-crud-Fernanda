package RecycleViewHelper

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fernanda.hernandez.crudfernanda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.Claseconexion
import modelo.DataClassmascotas


class Adaptador(private var Datos: List<DataClassmascotas>) : RecyclerView.Adapter<ViewHolder>() {

    fun Actualizarlista(nuevoList: List<DataClassmascotas>) {
        Datos = nuevoList
        notifyDataSetChanged()  //notifica al recycle view  que hay datos nuevos


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
                objconexion?.prepareStatement("delete from MascotasFernanda where nombreMascota = ? ")!!
            deletemascota.setString(1, nombreMascota)

            val commit = objconexion?.prepareStatement("commit")!!
            commit.executeUpdate()


        }
        Datos = listadatos.toList()
        //notificar que los datos se cambiaron
        notifyItemRemoved(position)
        notifyDataSetChanged()
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

            //botones
            builder.setPositiveButton("si") { dialog, which ->
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
  }
}
