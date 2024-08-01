package edward.mamani.timer_quizzdos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import edward.mamani.timer_quizzdos.adapter.ViewPageAdapter
import edward.mamani.timer_quizzdos.databinding.ActivitySecondBinding
import edward.mamani.timer_quizzdos.fragments.EstadisticasFragment
import edward.mamani.timer_quizzdos.fragments.PreguntasFragment
import java.sql.Time
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {
    private val binding:ActivitySecondBinding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }
    private lateinit var temaClass: TemaClass
    private var listPregunta:MutableList<PreguntaClass> = mutableListOf()
    private var pauseOffSet:Long  = 0L
    private var pausePregutaOffSet:Long = 0L
    private var isPlay = false
    private var nroPreguntaActual = 1

    private val onClickBtnContinuar = fun (preguntaClass: PreguntaClass){
        Toast.makeText(this,"Continuar ${preguntaClass.numero_pregunta}",Toast.LENGTH_SHORT).show()
        nroPreguntaActual = preguntaClass.numero_pregunta
        binding.nextPregunta.text = nroPreguntaActual.toString()
        // ACTUALIZAMOS LA base de chronometro de pregunta
        binding.chronometerPregunta.base = SystemClock.elapsedRealtime() - (getTimePregunta() + preguntaClass.tiempo.time)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initData()
        initView()
        initializeViewPage()
        initChonometer()
    }

    private fun initData(){
        this.temaClass = intent.getParcelableExtra<TemaClass>("temasClass")!!
        this.listPregunta = PreguntaProvider.getPreguntaList(this, temaClass.id_tema)
        this.nroPreguntaActual = listPregunta.size + 1
        this.pauseOffSet  = sumaTiempoMilisegundo()
    }

    private fun initView() {
        binding.idTema.text = temaClass.id_tema.toString()
        binding.tema.text = temaClass.tema
        binding.nextPregunta.text = "$nroPreguntaActual"
    }

    private  fun initChonometer(){
        setChronometerBase()
        binding.start.setOnClickListener{
            if(!isPlay){ playChonometer() }
            else { stopChonometer() }
        }

        binding.siguiente.setOnClickListener{
            if(listPregunta.size < nroPreguntaActual){
                addListPregunta(it)
                initializeViewPage()
            }else{
                nroPreguntaActual++
            }
            // AÑADIMOS A listaPregunta<PreguntaClass> Y GUARDAMOS EN DB

            Toast.makeText(this, "$nroPreguntaActual",Toast.LENGTH_SHORT).show()
            binding.nextPregunta.text = "$nroPreguntaActual"
        }
    }

    private fun initializeViewPage() {
        val lista = listOf("Preguntas", "Estadistica")

        val lista2:List<Fragment> = listOf(PreguntasFragment(onClickBtnContinuar),EstadisticasFragment())

        binding.viewPager.adapter = ViewPageAdapter(this, lista2, listPregunta)

        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = lista[position]
        }.attach()
    }

    // FUNCIONES UTILIZADAS EN INIT
    private fun playChonometer(){
        binding.chronometer2.base = getBaseGeneral()
        binding.chronometerPregunta.base = getBasePregunta()
        binding.chronometer2.start()
        binding.chronometerPregunta.start()
        binding.start.setImageResource(R.drawable.boton_de_pausa)
        isPlay = true
    }

    private  fun stopChonometer(){
        pauseOffSet = getTimeGeneral()
        pausePregutaOffSet = getTimePregunta()
        binding.chronometer2.stop()
        binding.chronometerPregunta.stop()
        binding.start.setImageResource(R.drawable.boton_de_play)
        isPlay = false
    }

    private fun getTimeGeneral(): Long {
        var res:Long = 0L
        if (isPlay) res = SystemClock.elapsedRealtime() - binding.chronometer2.base
        else res = pauseOffSet
        return res
    }

    private fun getTimePregunta():Long{
        var res:Long = 0L
        if (isPlay) res = SystemClock.elapsedRealtime() - binding.chronometerPregunta.base
        else res = pausePregutaOffSet
        return res
    }

    private fun sumaTiempoMilisegundo(): Long {
        var res: Long = 0L
        for (item in listPregunta) {
            // Asumiendo que item.tiempo es una cadena en formato "HH:MM:SS"
            val tiempoParts = item.tiempo.toString().split(":")
            val horas = tiempoParts[0].toLong()
            val minutos = tiempoParts[1].toLong()
            val segundos = tiempoParts[2].toLong()

            val tiempoEnSegundos = TimeUnit.HOURS.toSeconds(horas) +
                    TimeUnit.MINUTES.toSeconds(minutos) +
                    segundos
            res += tiempoEnSegundos
        }
        return TimeUnit.SECONDS.toMillis(res)
    }

    private fun setChronometerBase() {
        binding.chronometer2.base = SystemClock.elapsedRealtime() -  sumaTiempoMilisegundo()
    }

    private fun getBasePregunta():Long = SystemClock.elapsedRealtime() - this.pausePregutaOffSet

    private fun getBaseGeneral():Long = SystemClock.elapsedRealtime() - this.pauseOffSet

    fun onRadioButtonClicked(view: View) {

        if(!(isPlay || pausePregutaOffSet != 0L)) {
            getAlternativa(view)
            return
        }

        // AÑADIMOS A listaPregunta<PreguntaClass> Y GUARDAMOS EN DB
        val res:String = addListPregunta(view)
        initializeViewPage()
        Toast.makeText(this, res,Toast.LENGTH_SHORT).show()

        binding.nextPregunta.text = nroPreguntaActual.toString()

        // INICIALIZAMOS EL RELOJ DE PREGUNTA
        this.pausePregutaOffSet = 0L
        binding.chronometerPregunta.base = getBasePregunta()

        //___________________________________________________________________________________________________________
        if (!isPlay){
            //DESABILITAMOS EL ACUMULADOR DE PREGUNTAS
            Toast.makeText(this,"Anular Acumulador",Toast.LENGTH_SHORT).show()
        }
    }

    private fun addListPregunta(view: View):String{
        var str  = ""
        // OPTENEMOS LA ALTERNATIVA MARCADA
        var checked: Boolean = true
        var alternativa = '-'
        var tiempo:Time = Time(0L)
        try {
            checked = (view as RadioButton).isChecked
            alternativa = getAlternativa(view)
            tiempo = Time(getTimePregunta())
        }catch (e:Exception)
        {
            print(e.message)
        }
        if (!checked) return "No hay Check"

        // AÑADIMOS A DATABASE Y AL listPregunta<PreguntaClass>

        val preguntaClass:PreguntaClass = PreguntaClass(
            0,
            temaClass.id_tema,
            nroPreguntaActual,
            alternativa,
            tiempo
        )
        val sqlManager = SQLManager(this)
        // Retorna ACTUALIZADO = 0  Y INSERCION = 1
        val response  = sqlManager.addPregunta(this, preguntaClass)

        var index = 0
        if (SQLManager.INSERT == response){
            str = "$alternativa AGREGADO"
        } else if (SQLManager.UPDATE  == response) {
            index = listPregunta.size - preguntaClass.numero_pregunta
            listPregunta.removeAt(index)
            str =  "ACTUALIZADO"
        }

        str = "${preguntaClass.numero_pregunta} " + str
        listPregunta.add(index, preguntaClass)
        nroPreguntaActual = listPregunta.size + 1
        return  if (str=="") "Fallo al agregar pregunta" else str
    }

    private fun getAlternativa(view: View):Char{
        val altern:Char = when (view.id) {
            R.id.radioButton1 -> {'A' }
            R.id.radioButton2 -> {'B' }
            R.id.radioButton3 -> {'C' }
            R.id.radioButton4 -> {'D' }
            R.id.radioButton5 -> {'E' }
            else -> { '-' }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            binding.radioButton6.isChecked = true
        }, 1500L)

        return  altern
    }
}

