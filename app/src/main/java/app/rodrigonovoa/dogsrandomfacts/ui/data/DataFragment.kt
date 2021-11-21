package app.rodrigonovoa.dogsrandomfacts.ui.data

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.common.Prefs
import app.rodrigonovoa.dogsrandomfacts.common.Singleton
import app.rodrigonovoa.dogsrandomfacts.database.FactRoomDb
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import kotlinx.coroutines.launch

class DataFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataViewModel =
                ViewModelProvider(this).get(DataViewModel::class.java)

        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Singleton.getRepository()

        val tv_username = view.findViewById<TextView>(R.id.tv_username)
        val tv_facts = view.findViewById<TextView>(R.id.tv_facts)
        val tv_favs = view.findViewById<TextView>(R.id.tv_favs)
        val tv_shared = view.findViewById<TextView>(R.id.tv_shareds)
        val tv_opened = view.findViewById<TextView>(R.id.tv_opened)
        val tv_contactemail = view.findViewById<TextView>(R.id.tv_contactEmail)
        val tv_skip_splash = view.findViewById<TextView>(R.id.tv_skipsplash)

        val imv_contactemail = view.findViewById<ImageView>(R.id.imv_contactEmail)
        val imv_about = view.findViewById<ImageView>(R.id.imv_about_us)
        val imv_skip_splash = view.findViewById<ImageView>(R.id.imv_skipsplash)
        val imv_change_user = view.findViewById<ImageView>(R.id.imv_change_username)

        val prefs = Prefs(requireContext())

        if(prefs.skip_splash){
            tv_skip_splash.text = getString(R.string.data_data_skip)
        }else{
            tv_skip_splash.text = getString(R.string.data_data_no_skip)
        }

        imv_contactemail.setOnClickListener(){
            if(tv_contactemail.visibility == View.VISIBLE){
                tv_contactemail.visibility = View.INVISIBLE
            }else{
                tv_contactemail.visibility = View.VISIBLE
            }

        }

        imv_about.setOnClickListener(){
            val dialog = Dialog(requireActivity())
            dataViewModel.openAboutUsDialog(dialog)
        }

        imv_skip_splash.setOnClickListener(){

            if(prefs.skip_splash){
                prefs.skip_splash = false
                tv_skip_splash.text = getString(R.string.data_data_no_skip)
            }else{
                tv_skip_splash.text = getString(R.string.data_data_skip)
                prefs.skip_splash = true
            }
        }

        imv_change_user.setOnClickListener(){
            val dialog = Dialog(requireActivity())
            dataViewModel.openUsernameDialog(repository,dialog, tv_username, prefs)
        }


        dataViewModel.loadDataFromDb(repository, tv_username, tv_facts, tv_favs, tv_shared, tv_opened)
    }

}