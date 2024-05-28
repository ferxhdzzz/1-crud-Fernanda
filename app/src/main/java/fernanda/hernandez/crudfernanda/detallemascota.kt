package fernanda.hernandez.crudfernanda

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detallemascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detallemascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //recibir valores de adpatador
        val UUIDdetalle = intent.getStringExtra("mascotaUUID")
        val nombrerecibido= intent.getStringExtra("nombremascota")
        val edad = intent.getIntExtra("edad", 0)
        val peso = intent.getIntExtra("peso", 0)

        //mandae elementos a la pantalla

        val txtuuiddetalle = findViewById<TextView>(R.id.txtuuiddetalle)
        val txtnombredetalle = findViewById<TextView>(R.id.txtnombredetalle)
        val txtedaddetalle = findViewById<TextView>(R.id.txtedaddetalle)
        val txtpesodetalle = findViewById<TextView>(R.id.txtpesodetalle)

        txtuuiddetalle.text = UUIDdetalle
        txtnombredetalle.text = nombrerecibido
        txtedaddetalle.text = edad.toString()
        txtpesodetalle.text = peso.toString()




    }
}