package livrokotlin.com.lista_de_compas

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var produtosAdapter: ProdutoAdapter
    private lateinit var dbHelper: ListaComprasDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar o banco
        dbHelper = ListaComprasDatabase.getInstance(this)

        val btn_Adicionar = findViewById<Button>(R.id.btn_adicionar)
        val listViewProdutos = findViewById<ListView>(R.id.list_view_produtos)

        produtosAdapter = ProdutoAdapter(this)
        listViewProdutos.adapter = produtosAdapter

        btn_Adicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        // Configurar clique longo para excluir (segundo o livro)
        listViewProdutos.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, position, id ->

                // Buscando o item clicado
                val item = produtosAdapter.getItem(position)

                if (item != null) {
                    // Removendo o item clicado da lista
                    produtosAdapter.remove(item)

                    // Deletando do banco de dados
                    deletarProduto(item.id)

                    // Avisar o usuário (toast)
                    Toast.makeText(this, "Item deletado com sucesso", Toast.LENGTH_SHORT).show()

                    // Atualizar total
                    atualizarTotal()
                }
                true
            }

        // Carregar produtos inicialmente
        carregarProdutosDoBanco()
    }

    override fun onResume() {
        super.onResume()
        // Recarregar sempre que voltar à activity
        carregarProdutosDoBanco()
    }

    // Função para deletar produto (segundo a lógica do livro)
    private fun deletarProduto(idProduto: Int) {
        val db = dbHelper.writableDatabase

        // CORREÇÃO: Usando a sintaxe similar ao livro
        // delete("produtos", "id = {id}", "id" to idProduto)
        // Em SQLite puro, ficaria:
        db.delete("produtos", "id = ?", arrayOf(idProduto.toString()))

        db.close()
    }

    private fun carregarProdutosDoBanco() {
        // Limpar o adapter
        produtosAdapter.clear()

        // Buscar produtos do banco
        val listaProdutos = dbHelper.buscarTodosProdutos()

        // Adicionar ao adapter
        produtosAdapter.addAll(listaProdutos)
        produtosAdapter.notifyDataSetChanged()

        // Calcular total
        atualizarTotal()
    }

    private fun atualizarTotal() {
        val txt_total = findViewById<TextView>(R.id.txt_total)

        var soma = 0.0

        // Calcular soma baseada nos itens do adapter
        for (i in 0 until produtosAdapter.count) {
            val produto = produtosAdapter.getItem(i)
            produto?.let {
                soma += it.valor * it.quantidade
            }
        }

        // Formatar como moeda
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txt_total.text = "TOTAL: ${f.format(soma)}"
    }
}