package edward.mamani.timer_quizzdos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edward.mamani.timer_quizzdos.databinding.ActivityAddTemaBinding

class AddTemaActivity : AppCompatActivity() {

    private val binding: ActivityAddTemaBinding by lazy{
        ActivityAddTemaBinding.inflate(layoutInflater)
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
        binding.btnAgregar.setOnClickListener {
            if (testTema()) {
                val mensaje = setDataTema()
                val intent:Intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "El dato el obligatorio", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun testTema(): Boolean {
        var response = true
        if (binding.temaName.text.isEmpty()) {
            response = false
        }
        return response
    }

    private fun setDataTema():String{
        val dato = TemaClass(0, binding.temaName.text.toString())
        val sqlManager = SQLManager(this)
        val response = sqlManager.addTema(this,dato)

        if (response){
            return "Datos almacenado"
        }
        return "Los dato no se pudieron almacenar"

    }
}