package livrokotlin.com.lista_de_compas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val Context.database: ListaComprasDatabase
    get() = ListaComprasDatabase.getInstance(getApplicationContext())
class ListaComprasDatabase (context: Context): SQLiteOpenHelper(context,"listaCompras.db",null,1){

    companion object{
        private var instance: ListaComprasDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ListaComprasDatabase{
            if (instance == null){
                instance = ListaComprasDatabase(ctx.applicationContext)
            }
            return instance!!
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
    CREATE TABLE IF NOT EXISTS produtos(
    id  INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT,
    quantidade INTEGER,
    valor REAL,
    foto BLOB
    )
""")
    }
    // Adicione este método à sua classe ListaComprasDatabase
    fun buscarTodosProdutos(): List<Produto> {
        val produtos = mutableListOf<Produto>()
        val db = this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM produtos", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))

                // A quantidade está como INTEGER no banco
                val quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"))

                val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))

                // Buscar a foto (pode ser null)
                val foto: ByteArray? = if (!cursor.isNull(cursor.getColumnIndexOrThrow("foto"))) {
                    cursor.getBlob(cursor.getColumnIndexOrThrow("foto"))
                } else {
                    null
                }

                // Converter ByteArray para Bitmap (se existir)
                val bitmapFoto = if (foto != null && foto.isNotEmpty()) {
                    // Você precisa da extensão toBitmap() do Utils.kt
                    foto.toBitMap()
                } else {
                    null
                }

                // Criar objeto Produto
                val produto = Produto(id, nome, quantidade, valor, bitmapFoto)
                produtos.add(produto)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return produtos
    }

    // Adicione também um método para excluir
    fun excluirProduto(id: Int): Int {
        val db = this.writableDatabase
        val resultado = db.delete("produtos", "id = ?", arrayOf(id.toString()))
        db.close()
        return resultado
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS produtos")
        onCreate(db)
    }

}