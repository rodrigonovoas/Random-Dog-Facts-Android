package app.rodrigonovoa.dogsrandomfacts.ui.facts

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.database.FactModel
import app.rodrigonovoa.dogsrandomfacts.database.FactRoomDb
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository


class FactsFragment : Fragment() {

    lateinit var factsViewModel: FactsViewModel
    lateinit var pb_facts:ProgressBar
    lateinit var sw_facts:Switch
    lateinit var layout_view:View
    lateinit var repository: FactsRepository

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        factsViewModel =
                ViewModelProvider(this).get(FactsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_facts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factsDB = FactRoomDb.getDatabase(requireContext(), lifecycleScope)

        sw_facts = view.findViewById<Switch>(R.id.sw_facts)
        val imv_icon = view.findViewById<ImageView>(R.id.imv_icon)
        pb_facts = view.findViewById<ProgressBar>(R.id.pb_facts)
        layout_view = view

        repository = FactsRepository(factsDB)

        val llm = LinearLayoutManager(this@FactsFragment.requireContext())

        factsViewModel.init()

        factsViewModel.setData(repository)

        factsViewModel.reloadFragment().observe(viewLifecycleOwner, Observer { it ->
            if(it){
                imv_icon.setImageDrawable(resources.getDrawable(R.drawable.facts))
                factsViewModel.changeFavsToFacts(llm, view, factsViewModel.getFactsList(), factsViewModel.getFavsIdList(), this)
                pb_facts.visibility = View.INVISIBLE
            }
        });

        sw_facts.setOnClickListener(){

            if(sw_facts.isChecked){
                imv_icon.setImageDrawable(resources.getDrawable(R.drawable.fav))
                reloadFavsList()
                //factsViewModel.changeFactsToFavs(llm,view,factsViewModel.getFactsList(),factsViewModel.getFavsList(), this)
            }else{
                imv_icon.setImageDrawable(resources.getDrawable(R.drawable.facts))
                factsViewModel.changeFavsToFacts(llm,view,factsViewModel.getFactsList(), factsViewModel.getFavsIdList(), this)
            }

        }
    }

    fun reloadFavsList(){
        val llm = LinearLayoutManager(this@FactsFragment.requireContext())
        factsViewModel.changeFactsToFavs(llm, layout_view, factsViewModel.getFactsList(),factsViewModel.getFavsList(), this)
    }

}