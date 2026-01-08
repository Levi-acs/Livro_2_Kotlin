package livrokotlin.com.lista_de_compas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val btn_inserir = findViewById<Button>(R.id.btn_inserir)

        val txt_produto = findViewById<EditText>(R.id.txt_produto)

        btn_inserir.setOnClickListener {
            val produto = txt_produto.text.toString()

            if (produto.isNotEmpty()) {
                //adicionando produto

                //limpando o campo de pesquisa
                txt_produto.text.clear()
            } else {
                // barrando caso usuario não digite nada no campo
                txt_produto.error = "Preencha um valor"
            }
        }

    }
}
