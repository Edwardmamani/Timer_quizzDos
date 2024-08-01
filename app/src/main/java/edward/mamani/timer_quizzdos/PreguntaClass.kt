package edward.mamani.timer_quizzdos

import android.os.Parcel
import android.os.Parcelable
import java.sql.Time
import java.util.concurrent.TimeUnit

data class DataEstadistica(
    var clave:String,
    var valor:Time
)
data class PreguntaClass(
    var id_pregunta: Int,
    var id_tema: Int,
    var numero_pregunta: Int,
    var alternativa: Char,
    var tiempo: Time
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()?.firstOrNull() ?: ' ',
        Time(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id_pregunta)
        parcel.writeInt(id_tema)
        parcel.writeInt(numero_pregunta)
        parcel.writeString(alternativa.toString())
        parcel.writeLong(tiempo.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PreguntaClass> {
        override fun createFromParcel(parcel: Parcel): PreguntaClass {
            return PreguntaClass(parcel)
        }

        override fun newArray(size: Int): Array<PreguntaClass?> {
            return arrayOfNulls(size)
        }
        fun calcularEstadisticas(listaPregunta: List<PreguntaClass>): List<DataEstadistica> {
            if (listaPregunta.isEmpty()) {
                return listOf(DataEstadistica("0 Preguntas", Time(0L)))
            }

            // Última pregunta
            val tamaño = listaPregunta.size
            val ultimaPregunta = DataEstadistica("Pregunta $tamaño", listaPregunta[0].tiempo)

            // Inicializar valores para el cálculo
            var mayorTiempo = listaPregunta[0].tiempo
            var menorTiempo = listaPregunta[0].tiempo
            var suma: Long = 0L

            // Recorrer la lista una sola vez para encontrar mayor, menor y sumar los tiempos
            for (pregunta in listaPregunta) {
                val tiempo = pregunta.tiempo.time
                if (tiempo > mayorTiempo.time) {
                    mayorTiempo = pregunta.tiempo
                }
                if (tiempo < menorTiempo.time) {
                    menorTiempo = pregunta.tiempo
                }
                suma += tiempo
            }

            val masLento = DataEstadistica("Más lento", mayorTiempo)
            val masRapido = DataEstadistica("Más rápido", menorTiempo)

            // Promedio del tiempo
            val promedioTiempo = Time(suma / listaPregunta.size)
            val promedio = DataEstadistica("Promedio", promedioTiempo)

            // Variación del tiempo (ejemplo básico, la variación real puede necesitar desviación estándar u otra medida)
            var sumaCuadrados: Long = 0L
            var promedioSecond = toSecond(promedioTiempo)
            for (pregunta in listaPregunta) {
                val diferencia = toSecond(pregunta.tiempo) - promedioSecond
                sumaCuadrados += diferencia * diferencia
            }
            val varianzaTiempo = Time((sumaCuadrados / listaPregunta.size)*1000L)
            val variacion = DataEstadistica("Variación", varianzaTiempo)

            return listOf(ultimaPregunta, masLento, masRapido, promedio, variacion)
        }

        fun toSecond(time:Time): Long {
            val str = time.toString()
            val tiempoParts = str.split(":")
            val horas = tiempoParts[0].toLong()
            val minutos = tiempoParts[1].toLong()
            val segundos = tiempoParts[2].toLong()

            val tiempoEnSegundos = TimeUnit.HOURS.toSeconds(horas) +
                    TimeUnit.MINUTES.toSeconds(minutos) +
                    segundos
            return tiempoEnSegundos
        }
    }
}
