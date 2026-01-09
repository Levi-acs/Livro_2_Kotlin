package livrokotlin.com.lista_de_compas

val produtosGlobal = mutableListOf<Produto>()

val soma = produtosGlobal.sumByDouble { it.valor * it.quantidade }