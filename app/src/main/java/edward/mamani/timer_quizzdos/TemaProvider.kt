package edward.mamani.timer_quizzdos

import android.content.Context

class TemaProvider {
    companion object{
        fun getTemaList(context: Context):List<TemaClass>{
            val sqlManager = SQLManager(context)

            return sqlManager.extraerDataTema()
        }
    }
}