package app.rodrigonovoa.dogsrandomfacts

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import app.rodrigonovoa.dogsrandomfacts.common.Prefs
import app.rodrigonovoa.dogsrandomfacts.common.Singleton
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.database.UserModel
import app.rodrigonovoa.dogsrandomfacts.databinding.ActivityMainBinding
import app.rodrigonovoa.dogsrandomfacts.model.Fact
import app.rodrigonovoa.dogsrandomfacts.service.WebService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = Prefs(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        if(prefs.name.equals("")){
            openUsernameDialog(prefs)
        }
    }

    private fun openUsernameDialog(prefs:Prefs){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_enter_username)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent);

        val continueBtn = dialog.findViewById(R.id.btn_continue) as Button
        val edt_username = dialog.findViewById(R.id.edt_username) as EditText

        continueBtn.setOnClickListener {
            val text = edt_username.text
            if(text.isEmpty()==false){
                prefs.name = text.toString()
            }

            insertUser(Singleton.getRepository(),text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }

    fun insertUser(repository: FactsRepository, username:String){
        val user = UserModel(1,username,0,0,0,0)

        lifecycleScope.launch {
            repository.insertUser(user)
        }
    }
}