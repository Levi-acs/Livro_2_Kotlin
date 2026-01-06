package livrokotlin.com.lista_de_compas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ListViewProdutos = findViewById<ListView>(R.id.list_view_produtos)

        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        val txt_produto = findViewById<EditText>(R.id.txt_produto)

        val produtosAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1)

        ListViewProdutos.adapter = produtosAdapter

       btn_inserir.setOnClickListener {
           val produto = txt_produto.text.toString()
           produtosAdapter.add(produto)
       }
    }
}