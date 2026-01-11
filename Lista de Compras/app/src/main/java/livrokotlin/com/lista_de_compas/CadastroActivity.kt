package livrokotlin.com.lista_de_compas

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 100
    var imageBitMap: Bitmap? = null
    lateinit var dbHelper: ListaComprasDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // INICIALIZAR O BANCO
        dbHelper = ListaComprasDatabase(this)

        val btn_inserir = findViewById<Button>(R.id.btn_inserir)
        val txt_produto = findViewById<EditText>(R.id.txt_produto)
        val txt_qtd = findViewById<EditText>(R.id.txt_quantidade)
        val txt_valor = findViewById<EditText>(R.id.txt_valor)
        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)

        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }

        btn_inserir.setOnClickListener {
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()

            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {

                // convertendo quantidade para int por conta do Delete
                val quantidadeInt = try {
                    qtd.toInt()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Quantidade deve ser um número", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put("nome", produto)
                    put("quantidade", quantidadeInt)
                    put("valor", valor.toDouble())
                    // Usando a extensão toByteArray() do Utils.kt
                    imageBitMap?.let { bitmap ->
                        put("foto", bitmap.toByteArray())
                    }
                }

                // insert retorna um Long
                val idProduto = db.insert("Produtos", null, values)

                // idProduto é Long, então comparamos com -1L
                if (idProduto != -1L) {
                    Toast.makeText(this, "Item inserido com sucesso", Toast.LENGTH_SHORT).show()
                    txt_produto.text.clear()
                    txt_qtd.text.clear()
                    txt_valor.text.clear()

                    // Limpar a imagem
                    imageBitMap = null
                    img_foto_produto.setImageResource(android.R.drawable.ic_menu_camera)

                    finish()
                } else {
                    Toast.makeText(this, "Erro ao inserir no banco de dados", Toast.LENGTH_SHORT).show()
                }

                db.close()

            } else {
                txt_produto.error =
                    if (txt_produto.text.isEmpty()) "Preencha o nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_valor.error = if (txt_valor.text.isEmpty()) "Preencha o valor" else null
            }
        }
    }

    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Selecione uma imagem"),
            COD_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)

        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                try {
                    val inputStream: InputStream? = contentResolver.openInputStream(data.data!!)
                    imageBitMap = BitmapFactory.decodeStream(inputStream)
                    img_foto_produto.setImageBitmap(imageBitMap)
                } catch (e: Exception) {
                    Toast.makeText(this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}