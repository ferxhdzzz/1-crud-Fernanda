package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fernanda.hernandez.crudfernanda.R


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.txtmascotacard)
    val imgeditar: ImageView = view.findViewById(R.id.imgeditar)
    val imgborrar: ImageView = view.findViewById(R.id.imgborrar)

}
