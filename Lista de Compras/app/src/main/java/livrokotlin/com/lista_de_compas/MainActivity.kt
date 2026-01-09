package livrokotlin.com.lista_de_compas

import android.content.Intent
import android.os.Bundle
import android.util.Log.v
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var produtosAdapter: ProdutoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_Adicionar = findViewById<Button>(R.id.btn_adicionar)

        val ListViewProdutos = findViewById<ListView>(R.id.list_view_produtos)

        produtosAdapter = ProdutoAdapter(this)

        ListViewProdutos.adapter = produtosAdapter

        btn_Adicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)

            startActivity(intent)
        }

        ListViewProdutos.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, position, id ->
                val item = produtosAdapter.getItem(position)

                if (item != null) {
                    produtosAdapter.remove(item)
                    produtosGlobal.remove(item)
                }

                true
            }


    }

    override fun onResume() {
        super.onResume()
        val txt_total = findViewById<TextView>(R.id.txt_total)

        produtosAdapter.clear()
        produtosAdapter.addAll(produtosGlobal)
        produtosAdapter.notifyDataSetChanged()


        val soma = produtosGlobal.sumByDouble {
            it.valor * it.quantidade
        }
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txt_total.text = "TOTAL:	${f.format(soma)}"

    }
}