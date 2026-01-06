package livroKotlin.com.br

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Arquivo de layout
        setContentView(R.layout.activity_main)

        // acessando o  spinner
        val spn_sexo = findViewById<Spinner>(R.id.spn_sexo)

        // acessando a caixa de idade
        val txt_idade = findViewById<EditText>(R.id.txt_idade)

        // acessando botão para calcular
        val btn_calcular = findViewById<Button>(R.id.btn_calcular)

        // acessando texto do resultado
        val txt_resultado = findViewById<TextView>(R.id.txt_resultado)

        spn_sexo.adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
            listOf("Masculino", "Feminino"))

        btn_calcular.setOnClickListener {
            // vamos usar para capturar o sexo
            val sexo = spn_sexo.selectedItem as String

            // guardando idade do usario
            val idade = txt_idade.text.toString().toInt()

            var resultado = 0
            if (sexo == "Masculino"){
                resultado = 65 - idade
            }else{
                resultado = 60 - idade
            }

            txt_resultado.text = "Faltam $resultado anos para você se aposentar"
        }
    }
}