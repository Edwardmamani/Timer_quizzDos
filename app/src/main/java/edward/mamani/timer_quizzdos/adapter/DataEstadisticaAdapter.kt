package edward.mamani.timer_quizzdos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.DataEstadistica
import edward.mamani.timer_quizzdos.R
import edward.mamani.timer_quizzdos.databinding.ItemDataEstadisticaBinding

class DataEstadisticaAdapter(private val listDataEstadistica:List<DataEstadistica>):RecyclerView.Adapter<DataEstadisticaAdapter.ViewHolder>() {
    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val binding: ItemDataEstadisticaBinding by lazy {
            ItemDataEstadisticaBinding.bind(view)
        }

        fun render(dataEstadistica: DataEstadistica){
            binding.dataEstadisticaKey.text = dataEstadistica.clave
            binding.dataEstadisticaValue.text = dataEstadistica.valor.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data_estadistica, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listDataEstadistica.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(listDataEstadistica[position])
    }
}