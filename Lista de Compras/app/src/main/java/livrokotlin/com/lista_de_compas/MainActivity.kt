package livrokotlin.com.lista_de_compas

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_adicionar = findViewById<Button>(R.id.btn_adicionar)

        val ListViewProdutos = findViewById<ListView>(R.id.list_view_produtos)

        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        ListViewProdutos.adapter = produtosAdapter

        btn_adicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)

            startActivity(intent)
        }

        ListViewProdutos.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, position, id ->
                val item = produtosAdapter.getItem(position)

                produtosAdapter.remove(item)

                true
            }
    }
}