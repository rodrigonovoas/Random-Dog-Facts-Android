package app.rodrigonovoa.dogsrandomfacts.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import app.rodrigonovoa.dogsrandomfacts.MainActivity
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.common.Prefs
import app.rodrigonovoa.dogsrandomfacts.common.Singleton
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.database.UserModel
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val prefs = Prefs(this)

        if(prefs.first_opening){
            insertUser(Singleton.getRepository(),"")
            prefs.first_opening = false
        }

        if(prefs.skip_splash==false){
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun insertUser(repository: FactsRepository, username:String){
        val user = UserModel(1,username,0,0,0,0)

        lifecycleScope.launch {
            repository.insertUser(user)
            repository.addOpenedNum()
        }
    }
}