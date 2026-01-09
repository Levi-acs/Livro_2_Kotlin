package livrokotlin.com.lista_de_compas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        val txt_produto = findViewById<EditText>(R.id.txt_produto)
        val txt_qtd = findViewById<EditText>(R.id.txt_quantidade)
        val txt_valor = findViewById<EditText>(R.id.txt_valor)

        btn_inserir.setOnClickListener {
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()

            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()){
                val prod = Produto(produto,qtd.toInt(),valor.toDouble())

                produtosGlobal.add(prod)

                println("✅ Produto adicionado!")
                println("📦 Nome: ${prod.nome}")
                println("🔢 Quantidade: ${prod.quantidade}")
                println("💰 Valor: R$ ${prod.valor}")
                println("📊 Total de produtos na lista: ${produtosGlobal.size}")
                println("-----------------------------------")

                txt_produto.text.clear()
                txt_qtd.text.clear()
                txt_valor.text.clear()

            }else{
                txt_produto.error = if (txt_produto.text.isEmpty())"Preencha o nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_valor.error = if (txt_valor.text.isEmpty()) "Preencha o valor" else null
            }



        }

    }
}
