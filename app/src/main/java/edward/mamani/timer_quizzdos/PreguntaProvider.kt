package edward.mamani.timer_quizzdos

import android.content.Context

class PreguntaProvider {
    companion object{

        fun getPreguntaList(context: Context, id_tema:Int): MutableList<PreguntaClass> {
            val sqlManager = SQLManager(context)
            return sqlManager.extraerDataPregunta(id_tema)
        }
    }
}