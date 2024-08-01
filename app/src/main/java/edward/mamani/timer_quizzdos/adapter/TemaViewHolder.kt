package edward.mamani.timer_quizzdos.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.SQLManager
import edward.mamani.timer_quizzdos.TemaClass
import edward.mamani.timer_quizzdos.databinding.ItemTemaBinding
import java.sql.Time

class TemaViewHolder(private val view:View):RecyclerView.ViewHolder(view) {
    private val binding: ItemTemaBinding by lazy {
        ItemTemaBinding.bind(view)
    }
    fun render(temaClass:TemaClass, onclickLisener:(temaClass:TemaClass)->Unit){
        binding.tema.text = temaClass.tema
        itemView.setOnClickListener{
            onclickLisener(temaClass)
        }
        initView(temaClass)
    }
    private fun initView(temaClass: TemaClass) {
        val db = SQLManager(view.context)
        val tiempTotalTema: Time = db.totalTiempoTema(temaClass.id_tema)
        binding.totalTime.text = tiempTotalTema.toString()
    }
}