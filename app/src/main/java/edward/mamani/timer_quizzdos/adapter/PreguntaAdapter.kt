package edward.mamani.timer_quizzdos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.PreguntaClass
import edward.mamani.timer_quizzdos.R

class PreguntaAdapter(
    val preguntaList: List<PreguntaClass>,
    private val onClickBtnContinuar: (preguntaClass: PreguntaClass)->Unit
)
    : RecyclerView.Adapter<PreguntaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_pregunta,parent,false)
        return PreguntaViewHolder(view, onClickBtnContinuar)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        holder.render(preguntaList[position])
    }

    override fun getItemCount(): Int  = preguntaList.size
}