package RecycleViewHelper

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

    fun Actualizarlista (nuevoList: List<DataClassmascotas>){
        Datos = nuevoList
        notifyDataSetChanged()  //notifica al recycle view  que hay datos nuevos


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_itemcard, parent, false)

        return ViewHolder(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.nombremascotas

    }


}
