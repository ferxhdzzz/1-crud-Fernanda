package fernanda.hernandez.crudfernanda

import RecycleViewHelper.Adaptador
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.Claseconexion
import modelo.DataClassmascotas

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtnombre = findViewById<EditText>(R.id.txtnombre)
        val txtpeso = findViewById<EditText>(R.id.txtpeso)
        val txtedad = findViewById<EditText>(R.id.txtedad)
        val btnagregar = findViewById<Button>(R.id.btnagregar)
        val rcbmascotas = findViewById<RecyclerView>(R.id.rcdMascotas)

        //Asignarle un layout al Recycleview
        rcbmascotas.layoutManager = LinearLayoutManager(this)

        //funcion para mostarar los datos
        fun obtenerdatos(): List<DataClassmascotas>{

            //objeto para la clase conexion
            val objConexion = Claseconexion().cadenaConexion()

            //Crear statements
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from MascotasFernanda") !!
            val mascotas = mutableListOf<DataClassmascotas>()

            //recorro todos los registros de la base de datos
            while (resulSet.next())  {
                val nombre = resulSet.getString("nombreMascota")
                val mascota = DataClassmascotas(nombre)
                mascotas.add(mascota)
            }
            return mascotas
        }

        //Asignar el adaptador
        CoroutineScope(Dispatchers.IO).launch {
            val mascotasDB = obtenerdatos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(mascotasDB)
                rcbmascotas.adapter = adapter
            }
        }

        btnagregar.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                // 1 creo un objeto de la clase conexion
                val objconexion = Claseconexion(). cadenaConexion()

                // 2 creo una variable que contenga PrepareStatement
                val addMascota = objconexion?.prepareStatement ("insert into MascotasFernanda values (?, ?, ?)")!!
                addMascota.setString(1, txtnombre.text.toString())
                addMascota.setInt(2, txtpeso.text.toString().toInt())
                addMascota.setInt(3, txtedad.text.toString().toInt())
                addMascota.executeUpdate()



            }

        }

    }
}