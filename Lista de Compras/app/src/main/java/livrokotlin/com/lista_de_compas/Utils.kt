package livrokotlin.com.lista_de_compas

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

val produtosGlobal = mutableListOf<Produto>()

val soma = produtosGlobal.sumByDouble { it.valor * it.quantidade }

fun Bitmap.toByteArray(): ByteArray{
    val stream = ByteArrayOutputStream()

    this.compress(android.graphics.Bitmap.CompressFormat.PNG,0,stream)

    return stream.toByteArray()
}

fun ByteArray.toBitMap() : Bitmap{
    return BitmapFactory.decodeByteArray(this,0,this.size)
}