package edward.mamani.timer_quizzdos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.DataEstadistica
import edward.mamani.timer_quizzdos.PreguntaClass
import edward.mamani.timer_quizzdos.R
import edward.mamani.timer_quizzdos.TemaClass
import edward.mamani.timer_quizzdos.adapter.PreguntaAdapter
import edward.mamani.timer_quizzdos.adapter.ViewPageAdapter
import edward.mamani.timer_quizzdos.databinding.FragmentPreguntasBinding


class PreguntasFragment (private val onClickBtnContinuar: (preguntaClass: PreguntaClass)->Unit) : Fragment(R.layout.fragment_preguntas) {
    private var _binding: FragmentPreguntasBinding? = null
    private val binding get() = _binding!!

    private var listPregunta:List<PreguntaClass> = listOf<PreguntaClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreguntasBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // recuperamos los datos
        arguments?.let {
            listPregunta = it.getParcelableArrayList("object_list_pregunta")!!
        }
        initRecyclerPregunta()

    }
    private fun initRecyclerPregunta(){
        val customAdater = PreguntaAdapter(listPregunta, onClickBtnContinuar)
        val recyclerView: RecyclerView = binding.recyclerViewPregunta
        recyclerView.adapter = customAdater
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}