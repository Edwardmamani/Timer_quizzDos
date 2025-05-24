package edward.mamani.timer_quizzdos.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.PreguntaClass
import edward.mamani.timer_quizzdos.databinding.ItemPreguntaBinding

class PreguntaViewHolder(view:View, private val onClickBtnContinuar: (preguntaClass: PreguntaClass)->Unit): RecyclerView.ViewHolder(view) {
    private val binding: ItemPreguntaBinding by lazy {
        ItemPreguntaBinding.bind(view)
    }
    fun  render(preguntaClass: PreguntaClass){
        binding.numeroPregunta.text = preguntaClass.numero_pregunta.toString()
        binding.alternativa.text = preguntaClass.alternativa.toString()
        binding.tiempo.text = preguntaClass.tiempo.toString()
        binding.btnContinuar.setOnClickListener{
            onClickBtnContinuar(preguntaClass)
        }
    }
}