package edward.mamani.timer_quizzdos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import edward.mamani.timer_quizzdos.DataEstadistica
import edward.mamani.timer_quizzdos.PreguntaClass
import edward.mamani.timer_quizzdos.R
import edward.mamani.timer_quizzdos.adapter.DataEstadisticaAdapter
import edward.mamani.timer_quizzdos.databinding.FragmentEstadisticasBinding


class EstadisticasFragment : Fragment() {
    private var _binding: FragmentEstadisticasBinding? = null
    private val binding get() = _binding!!

    private var listPregunta:List<PreguntaClass> = listOf<PreguntaClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstadisticasBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // recuperamos los datos
        arguments?.let {
            listPregunta = it.getParcelableArrayList("object_list_pregunta")!!
        }
        val dataEstadistica:List<DataEstadistica> = PreguntaClass.calcularEstadisticas(listPregunta)
        initRecyclerPregunta(dataEstadistica)
    }

    private fun initRecyclerPregunta(dataEstadistica:List<DataEstadistica>) {
        val adapter = DataEstadisticaAdapter(dataEstadistica)
        val recyclerView: RecyclerView = binding.recyclerViewDataEstadisctica
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}