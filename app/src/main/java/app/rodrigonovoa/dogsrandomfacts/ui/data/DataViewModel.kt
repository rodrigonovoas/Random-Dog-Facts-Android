package app.rodrigonovoa.dogsrandomfacts.ui.data

import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.database.UserModel
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {

    fun loadDataFromDb(repository: FactsRepository, tv_username:TextView, tv_facts: TextView, tv_favs:TextView, tv_shared:TextView, tv_opened:TextView){

        viewModelScope.launch {
            tv_username.text = tv_username.text.toString() + repository.getUsername()
            tv_facts.text = tv_facts.text.toString() + repository.getFactsNum().toString()
            tv_favs.text = tv_favs.text.toString() + repository.getFavsNum().toString()
            tv_shared.text = tv_shared.text.toString() + repository.getSharedNum().toString()
            tv_opened.text = tv_opened.text.toString() + repository.getOpenedNum().toString()
        }

    }

    fun openAboutUsDialog(dialog:Dialog){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_about_us)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent);

        val returnBtn = dialog.findViewById(R.id.btn_return) as Button

        returnBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun openUsernameDialog(repository: FactsRepository, dialog:Dialog, tv_username:TextView){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_enter_username)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent);

        val btn_accept = dialog.findViewById(R.id.btn_continue) as Button
        val edt_username = dialog.findViewById(R.id.edt_username) as EditText

        val context = btn_accept.context

        btn_accept.setOnClickListener(){
            if(edt_username.text.isEmpty()==false){
                val username = edt_username.text.toString()
                insertUser(repository, username)
                tv_username.text = context.getString(R.string.data_data_username) + username
                dialog.dismiss()
            }else{
                Toast.makeText(btn_accept.context,context.getString(R.string.username_dialog_empty),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        dialog.show()
    }

    private fun insertUser(repository: FactsRepository, username:String){
        viewModelScope.launch {
            repository.updateUsername(username)
        }
    }
}