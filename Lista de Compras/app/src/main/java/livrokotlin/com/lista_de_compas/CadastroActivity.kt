package livrokotlin.com.lista_de_compas

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 100
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val btn_inserir = findViewById<Button>(R.id.btn_inserir)
        val txt_produto = findViewById<EditText>(R.id.txt_produto)
        val txt_qtd = findViewById<EditText>(R.id.txt_quantidade)
        val txt_valor = findViewById<EditText>(R.id.txt_valor)
        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)

        // Adicionei o listener para a imagem
        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }

        btn_inserir.setOnClickListener {
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()

            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {
                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)

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
                imageBitMap = null
                img_foto_produto.setImageResource(android.R.drawable.ic_menu_camera)

                finish()

            } else {
                txt_produto.error =
                    if (txt_produto.text.isEmpty()) "Preencha o nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_valor.error = if (txt_valor.text.isEmpty()) "Preencha o valor" else null
            }
        }
    }

    // Adicionei a função abrirGaleria aqui dentro da classe
    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Selecione uma imagem"),
            COD_IMAGE
        )
    }

    // Adicionei o onActivityResult aqui dentro da classe
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val inputStream: InputStream? = contentResolver.openInputStream(data.data!!)
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }
}