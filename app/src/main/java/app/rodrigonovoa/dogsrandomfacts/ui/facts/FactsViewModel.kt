package app.rodrigonovoa.dogsrandomfacts.ui.facts

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.database.FactModel
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.database.FavModel
import app.rodrigonovoa.dogsrandomfacts.ui.facts.adapters.FactsRecyclerAdapter
import app.rodrigonovoa.dogsrandomfacts.ui.facts.adapters.FavRecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactsViewModel : ViewModel() {

    private lateinit var  facts_list:MutableList<FactModel>
    private lateinit var  favs_list:MutableList<FavModel>
    private lateinit var reload_fragment:MutableLiveData<Boolean>

    fun init(){
        reload_fragment = MutableLiveData<Boolean>()
    }

    fun reloadFragment():MutableLiveData<Boolean>{
        return reload_fragment
    }

    fun setFactsList(list:MutableList<FactModel>){
        this.facts_list = list
    }

    fun getFactsList():MutableList<FactModel>{
        return this.facts_list
    }

    fun setFavsList(list:MutableList<FavModel>){
        this.favs_list = list
    }

    fun getFavsList():MutableList<FavModel>{
        return favs_list
    }

    fun setData(repository:FactsRepository, pb:ProgressBar){
        viewModelScope.launch(Dispatchers.IO) {
            setFactsList(repository.getFacts().toMutableList())
            setFavsList(repository.getFavs().toMutableList())

            reload_fragment.postValue(true)
        }
    }

    fun changeFavsToFacts(llm:LinearLayoutManager ,view: View, list:MutableList<FactModel>, fragment:FactsFragment){
        llm.orientation = LinearLayoutManager.VERTICAL
        val adapter = FactsRecyclerAdapter(list!!, fragment)
        val recycler = view.findViewById<RecyclerView>(R.id.rc_facts)
        recycler.setLayoutManager(llm)
        recycler.adapter = adapter
    }

    fun changeFactsToFavs(llm:LinearLayoutManager, view: View, list:MutableList<FactModel>, fav_list:MutableList<FavModel>, fragment:FactsFragment){
        llm.orientation = LinearLayoutManager.VERTICAL
        val adapter = FavRecyclerAdapter(fav_list!!, list!!, fragment)
        val recycler = view.findViewById<RecyclerView>(R.id.rc_facts)
        recycler.setLayoutManager(llm)
        recycler.adapter = adapter
    }
}