package edward.mamani.timer_quizzdos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import edward.mamani.timer_quizzdos.adapter.TemaAdapter
import edward.mamani.timer_quizzdos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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
        initRecyclerView()
        binding.btnAddTema.setOnClickListener{
            val intent = Intent(this, AddTemaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        val dataset:List<TemaClass> = TemaProvider.getTemaList(this)

        val customAdapter = TemaAdapter(dataset){ temaClass:TemaClass ->
            itemSelectTema(temaClass)
        }

        val recyclerView: RecyclerView = binding.recyclerViewTemas
        // recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
    }

    private fun itemSelectTema(temaClass: TemaClass){
        val explicitIntent = Intent(this,SecondActivity::class.java)
        explicitIntent.putExtra("temasClass",temaClass)
        startActivity(explicitIntent)
    }
}