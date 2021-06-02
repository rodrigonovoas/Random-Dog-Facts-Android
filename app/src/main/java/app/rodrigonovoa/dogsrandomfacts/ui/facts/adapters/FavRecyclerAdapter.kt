package app.rodrigonovoa.dogsrandomfacts.ui.facts.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.common.Singleton
import app.rodrigonovoa.dogsrandomfacts.database.FactModel
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.database.FavModel
import app.rodrigonovoa.dogsrandomfacts.ui.facts.FactsFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavRecyclerAdapter(private val favDataList: MutableList<FavModel>, private val factDataList: MutableList<FactModel>, private val fragment:FactsFragment) :
    RecyclerView.Adapter<FavRecyclerAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var tv_fact: TextView
        lateinit var imv_dog: ImageView
        lateinit var ll_main: LinearLayout

        init {
            tv_fact = view.findViewById(R.id.tv_fact_row)
            imv_dog = view.findViewById(R.id.imv_dog_row)
            ll_main = view.findViewById(R.id.ll_main_row)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_facts, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val id = favDataList.get(position).factid
        val fav_id = favDataList.get(position).id
        viewHolder.tv_fact.text = factDataList.get(id - 1).fact

        viewHolder.ll_main.setOnClickListener(){
            openDeleteFavDialog(viewHolder.tv_fact.context, fav_id, Singleton.getRepository())
        }

        getRandomPetDialog(viewHolder.tv_fact.context, viewHolder.imv_dog, viewHolder.tv_fact, viewHolder.ll_main)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = favDataList.size

    private fun getRandomPetDialog(
        context: Context,
        imv_dog: ImageView,
        tv_fact: TextView,
        ll_main: LinearLayout
    ) {
        var selected_dog = (1..4).random()
        val resource = context.resources

        tv_fact.setTextColor(resource.getColor(R.color.white))

        if (selected_dog == 1) {
            imv_dog.setImageDrawable(resource.getDrawable(R.drawable.dog1))
            tv_fact.background = resource.getDrawable(R.drawable.dog1_dialogue_box)

        } else if (selected_dog == 2) {
            imv_dog.setImageDrawable(resource.getDrawable(R.drawable.dog2))
            tv_fact.background = resource.getDrawable(R.drawable.dog2_dialogue_box)

        } else if (selected_dog == 3) {
            imv_dog.setImageDrawable(resource.getDrawable(R.drawable.dog3))
            tv_fact.background = resource.getDrawable(R.drawable.dog3_dialogue_box)
            tv_fact.setTextColor(resource.getColor(R.color.dog1_color_2))

        } else if (selected_dog == 4) {
            imv_dog.setImageDrawable(resource.getDrawable(R.drawable.dog4))
            tv_fact.background = resource.getDrawable(R.drawable.dog4_dialogue_box)
        }

        ll_main.setPadding(10,10,10,10)
        tv_fact.setPadding(30,30,30,30)
    }

    private fun openDeleteFavDialog(context:Context, fav_id:Int, repository: FactsRepository){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Do you want to delete this fact from the list?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    deleteFav(repository, fav_id)
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->
                    return@OnClickListener
                })
        builder.create()

        builder.show()
    }

    private fun deleteFav(repository: FactsRepository, id:Int){

        GlobalScope.launch {
            repository.subFavNum()
            repository.deleteFavById(id)

            fragment.factsViewModel.setData(repository,fragment.pb_facts)
        }

        fragment.sw_facts.isChecked = false

        Toast.makeText(fragment.sw_facts.context, "Fav. deleted", Toast.LENGTH_SHORT).show()
    }

}