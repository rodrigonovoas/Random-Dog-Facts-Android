package app.rodrigonovoa.dogsrandomfacts.ui.home

import android.content.Context
import android.util.Log
import android.util.Log.i
import android.view.View
import android.widget.*
import androidx.lifecycle.*
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.database.FactModel
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.database.FavModel
import app.rodrigonovoa.dogsrandomfacts.database.UserModel
import app.rodrigonovoa.dogsrandomfacts.model.DogImage
import app.rodrigonovoa.dogsrandomfacts.model.Fact
import app.rodrigonovoa.dogsrandomfacts.service.WebService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Console
import java.lang.Math.log

class HomeViewModel : ViewModel() {

    private lateinit var change_pet:MutableLiveData<Boolean>
    private lateinit var show_toast:MutableLiveData<Boolean>
    private lateinit var currentFact:FactModel
    private lateinit var petImageUrl:MutableLiveData<String>
    private var toast_text:String = ""

    fun init(){
        change_pet = MutableLiveData<Boolean>()
        petImageUrl = MutableLiveData<String>()
        show_toast = MutableLiveData<Boolean>()
    }

    fun setCurrentFact(fact:FactModel){
        this.currentFact = fact
    }

    fun getCurrentFact():FactModel{
        return this.currentFact
    }

    fun change_pet():MutableLiveData<Boolean>{
        return this.change_pet
    }

    fun changePetImg():MutableLiveData<String>{
        return this.petImageUrl
    }

    fun show_toast():MutableLiveData<Boolean>{
        return this.show_toast
    }

    fun getToastText():String{
        return toast_text
    }

    fun addFact(fact: Fact, repository: FactsRepository){
        val newFact = FactModel(0,fact.facts[0])
        viewModelScope.launch {
            repository.addFactNum()
            val id = repository.insertFact(newFact)
            setCurrentFact(FactModel(id.toInt(),fact.facts[0]))
        }
    }

   fun getFact(repository:FactsRepository, service:WebService, tv_facts: TextView){
        viewModelScope.launch {
            val client = service.returnClientInterface()

            val call: Call<Fact> = client.getSingleRandomFact()
            call!!.enqueue(object : Callback<Fact> {

                override fun onResponse(call: Call<Fact>, response: Response<Fact>) {
                    val fact: Fact

                    if (response.code() == 200){
                        fact = response.body()!!
                        if(fact != null){
                            setFactIntoView(tv_facts,fact)
                            addFact(fact, repository)

                            change_pet.value = true
                        }
                    }
                }

                override fun onFailure(call: Call<Fact>, t: Throwable) {
                    Log.d("WebService",t.message!!.toString())
                }
            })
        }
    }

    fun getDogImage(service:WebService, imv_dog:ImageView){
        viewModelScope.launch {
            val client = service.returnClientInterface()

            val call: Call<DogImage> = client.getImageFromApi()
            call!!.enqueue(object : Callback<DogImage> {

                override fun onResponse(call: Call<DogImage>, response: Response<DogImage>) {
                    val image: DogImage

                    if (response.code() == 200){
                        image = response.body()!!
                        petImageUrl.value = image.message
                    }
                }

                override fun onFailure(call: Call<DogImage>, t: Throwable) {
                    Log.d("WebService",t.message!!.toString())
                }
            })
        }
    }

    fun addFav(context: Context, repository: FactsRepository){
        val factid = getCurrentFact().id
        val newFav = FavModel(0,factid,1)

        viewModelScope.launch {
            val facts = repository.getFavByFactId(factid)

            if(facts.size == 0){
                toast_text = context.getString(R.string.facts_fav_added)
                repository.addFavNum()
                repository.insertFav(newFav)
            }else{
                toast_text = context.getString(R.string.facts_fav_duplicated)
            }

            show_toast.value = true
        }
    }

    fun addSharedNum(repository: FactsRepository){
        viewModelScope.launch {
            repository.addSharedNum()
        }
    }

    private fun setFactIntoView(tv_facts:TextView, fact:Fact){
        tv_facts.setText(fact.facts[0])
    }

    fun getRandomPetDialog(
        mainLayout: RelativeLayout,
        tv_title: TextView,
        imv_dog: ImageView,
        tv_fact: TextView
    ) {
        val context = tv_fact.context
        var selected_dog = (1..4).random()

        tv_fact.setTextColor(context.resources.getColor(R.color.white))
        if (selected_dog == 1) {
            mainLayout.background = context.resources.getDrawable(R.color.dog1_color_1)
            tv_title.setTextColor(context.resources.getColor(R.color.dog1_color_2))
            imv_dog.setImageDrawable(context.resources.getDrawable(R.drawable.dog1))
            tv_fact.background = context.resources.getDrawable(R.drawable.dog1_dialogue_box)

        } else if (selected_dog == 2) {
            mainLayout.background = context.resources.getDrawable(R.color.dog2_color_1)
            tv_title.setTextColor(context.resources.getColor(R.color.dog2_color_2))
            imv_dog.setImageDrawable(context.resources.getDrawable(R.drawable.dog2))
            tv_fact.background = context.resources.getDrawable(R.drawable.dog2_dialogue_box)

        } else if (selected_dog == 3) {
            mainLayout.background = context.resources.getDrawable(R.color.dog3_color_2)
            tv_title.setTextColor(context.resources.getColor(R.color.dog3_color_1))
            imv_dog.setImageDrawable(context.resources.getDrawable(R.drawable.dog3))
            tv_fact.background = context.resources.getDrawable(R.drawable.dog3_dialogue_box)
            tv_fact.setTextColor(context.resources.getColor(R.color.dog3_color_2))

        } else if (selected_dog == 4) {
            mainLayout.background = context.resources.getDrawable(R.color.dog4_color_1)
            tv_title.setTextColor(context.resources.getColor(R.color.dog4_color_2))
            imv_dog.setImageDrawable(context.resources.getDrawable(R.drawable.dog4))
            tv_fact.background = context.resources.getDrawable(R.drawable.dog4_dialogue_box)

        }

        tv_fact.setPadding(70,70,70,70)
    }
}