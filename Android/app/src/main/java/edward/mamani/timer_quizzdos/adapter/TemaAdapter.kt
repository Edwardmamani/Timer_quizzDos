package edward.mamani.timer_quizzdos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.R
import edward.mamani.timer_quizzdos.TemaClass

class TemaAdapter(val temaList:List<TemaClass>, private val onclickLisener:(temaClass:TemaClass)->Unit): RecyclerView.Adapter<TemaViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tema, parent, false)
        return  TemaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TemaViewHolder, position: Int) {
        val item = temaList[position]
        holder.render(item, onclickLisener)
    }

    override fun getItemCount(): Int = temaList.size
}