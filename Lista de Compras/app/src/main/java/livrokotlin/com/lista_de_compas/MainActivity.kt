package livrokotlin.com.lista_de_compas

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1)
    }
}