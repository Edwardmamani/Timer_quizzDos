package edward.mamani.timer_quizzdos.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import edward.mamani.timer_quizzdos.PreguntaClass
import edward.mamani.timer_quizzdos.TemaClass
import java.util.ArrayList

class ViewPageAdapter (
    fragmentActivity: FragmentActivity,
    private  val items: List<Fragment>,
    private val listPregunta:List<PreguntaClass>
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {

        val res = items[position]
        res.arguments = Bundle().apply {
            putParcelableArrayList("object_list_pregunta", ArrayList(listPregunta))
        }

        return res
    }
}

