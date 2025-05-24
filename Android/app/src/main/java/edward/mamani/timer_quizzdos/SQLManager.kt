package edward.mamani.timer_quizzdos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Time
import java.util.concurrent.TimeUnit

const val DBName = "timer_quizz4"

class SQLManager(context: Context) : SQLiteOpenHelper(context, DBName, null, 1) {
    private val NAME_TABLA_TEMA:String = "tema"
    private val NAME_TABLA_PREGUNTA:String = "pregunta"
    private val SQL_CREATE_TABLE_TEMA:String = ("CREATE TABLE $NAME_TABLA_TEMA ( " +
            " id_tema INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " tema VARCHAR(30) NOT NULL " +
            ");")
    private val SQL_CREATE_TABLE_PREGUNTA:String = ("CREATE TABLE $NAME_TABLA_PREGUNTA ( " +
            "    id_pregunta INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "    id_tema INTEGER NOT NULL, " +
            "    numero_pregunta INT, " +
            "    alternativa VARCHAR(1) CHECK (alternativa IN ('A', 'B', 'C', 'D', 'E', '-')), " +
            "    tiempo TIME(2), " +
            "    FOREIGN KEY (id_tema) REFERENCES $NAME_TABLA_TEMA(id_tema), " +
            "    UNIQUE (id_tema, numero_pregunta) " +
            ");")
    private val TEMAS_DEFAULT:List<String> = listOf(
        "INSERT INTO $NAME_TABLA_TEMA (id_tema, tema) VALUES (1, 'Vectores'); ",
        "INSERT INTO $NAME_TABLA_TEMA (id_tema, tema) VALUES (2, 'Ecuaciones de rectas'); ",
        "INSERT INTO $NAME_TABLA_TEMA (id_tema, tema) VALUES (3, 'Derivadas'); ",
        "INSERT INTO $NAME_TABLA_TEMA (id_tema, tema) VALUES (4, 'Integrales'); " ,
        "INSERT INTO $NAME_TABLA_TEMA (id_tema, tema) VALUES (5, 'Distribuciones de probabilidad');"
    )

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_TABLE_TEMA)
        db.execSQL(SQL_CREATE_TABLE_PREGUNTA)

        for(sql in TEMAS_DEFAULT) {
            db.execSQL(sql)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //
    }

    fun addTema(context: Context, datos: TemaClass): Boolean {
        var response = false
        val contentValues = ContentValues()

        contentValues.put("tema", datos.tema)

        val db = SQLManager(context)
        val manager = db.writableDatabase

        try {
            manager.insert(NAME_TABLA_TEMA, null, contentValues)
            response = true
        } catch (e: Exception) {
            print(e.message)
        } finally {
//            db.close()
        }

        return response
    }

    fun addPregunta(context: Context,datos:PreguntaClass): Int {
        var response = -1
        var contentValues:ContentValues = ContentValues()
        contentValues.put("id_tema",datos.id_tema)
        contentValues.put("numero_pregunta", datos.numero_pregunta)
        contentValues.put("alternativa",datos.alternativa.toString())
        contentValues.put("tiempo",datos.tiempo.toString())

        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {

            val respo = manager.insert(NAME_TABLA_PREGUNTA,null,contentValues)
            response = INSERT
            if(respo < 0L){
                manager.update(NAME_TABLA_PREGUNTA,contentValues, "id_tema=? AND numero_pregunta=?", arrayOf("${datos.id_tema}","${datos.numero_pregunta}"))
                response =  UPDATE
            }

        }catch (e:Exception){
            print(e.message)
        }finally {
//            db.close()
        }

        return  response
    }

    // extraerDataTema esta val db = this.readableDatabase
    fun extraerDataTema(): List<TemaClass> {
        val lista = mutableListOf<TemaClass>()
        val db = this.readableDatabase
        val sql = "SELECT * FROM $NAME_TABLA_TEMA ORDER BY id_tema DESC"

        db.rawQuery(sql, null).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id_tema"))
                    val tema = cursor.getString(cursor.getColumnIndexOrThrow("tema"))

                    lista.add(TemaClass(id, tema))
                } while (cursor.moveToNext())
            }
        }

        return lista
    }

    // extraerDataPregunta esta val db = this.readableDatabase
    fun extraerDataPregunta(id_tema: Int): MutableList<PreguntaClass> {
        val lista = mutableListOf<PreguntaClass>()
        val db = this.readableDatabase
        val sql = "SELECT * FROM $NAME_TABLA_PREGUNTA WHERE id_tema = ? ORDER BY numero_pregunta DESC"

        db.rawQuery(sql, arrayOf(id_tema.toString())).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id_pregunta = cursor.getInt(cursor.getColumnIndexOrThrow("id_pregunta"))
                    val idTema = cursor.getInt(cursor.getColumnIndexOrThrow("id_tema"))
                    val numero_pregunta = cursor.getInt(cursor.getColumnIndexOrThrow("numero_pregunta"))
                    val alternativa: Char = cursor.getString(cursor.getColumnIndexOrThrow("alternativa")).first()
                    val tiempo: Time = Time.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("tiempo")))
                    lista.add(PreguntaClass(id_pregunta, idTema, numero_pregunta, alternativa, tiempo))
                } while (cursor.moveToNext())
            }
        }
        return lista
    }

    fun totalTiempoTema(id_tema: Int): Time {
        val totalMilisegundo: Long = sumaTiempoMilisegundo(this.extraerDataPregunta(id_tema))
        return Time(totalMilisegundo)
    }

    private fun sumaTiempoMilisegundo(listPregunta:MutableList<PreguntaClass>): Long {
        var res: Long = 0L
        for (item in listPregunta) {
            // Asumiendo que item.tiempo es una cadena en formato "HH:MM:SS"
            val tiempoParts = item.tiempo.toString().split(":")
            val horas = tiempoParts[0].toLong()
            val minutos = tiempoParts[1].toLong()
            val segundos = tiempoParts[2].toLong()

            val tiempoEnSegundos = TimeUnit.HOURS.toSeconds(horas) +
                    TimeUnit.MINUTES.toSeconds(minutos) +
                    segundos
            res += tiempoEnSegundos
        }
        return TimeUnit.SECONDS.toMillis(res)
    }
    companion object {
        const val UPDATE = 1
        const val INSERT = 0
    }
}