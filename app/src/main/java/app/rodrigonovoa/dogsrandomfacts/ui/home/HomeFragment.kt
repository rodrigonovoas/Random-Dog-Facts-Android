package app.rodrigonovoa.dogsrandomfacts.ui.home


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.rodrigonovoa.dogsrandomfacts.R
import app.rodrigonovoa.dogsrandomfacts.common.Prefs
import app.rodrigonovoa.dogsrandomfacts.common.Singleton
import app.rodrigonovoa.dogsrandomfacts.service.WebService
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var prefs : Prefs

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = Prefs(requireContext())

        val repository = Singleton.getRepository()
        val service = WebService(requireContext())

        //home
        val tv_title:TextView = view.findViewById(R.id.tv_activity_title)
        val main_layout: RelativeLayout = view.findViewById(R.id.main_layout)
        val imv_api_dog:ImageView = view.findViewById(R.id.imv_dog_image)

        //card
        val tv_fact:TextView = view.findViewById(R.id.tv_fact_text)
        val imv_next_fact: ImageView = view.findViewById(R.id.imv_nextFact)
        val imv_share: ImageView = view.findViewById(R.id.imv_share)
        val imv_dog: ImageView = view.findViewById(R.id.imv_dog)
        val imv_fav: ImageView = view.findViewById(R.id.imv_fav)
        val imv_copy: ImageView = view.findViewById(R.id.imv_copy_text)
        val pb_fact: ProgressBar = view.findViewById(R.id.pb_fact)

        makeViewsInvisible(tv_fact, pb_fact)

        homeViewModel.init()
        homeViewModel.getFact(repository,service,tv_fact)
        homeViewModel.getDogImage(WebService(requireContext(),true), imv_api_dog)

        if(prefs.name != ""){
            tv_title.text = prefs.name + " , " + tv_title.text.toString()
        }

        homeViewModel.change_pet().observe(viewLifecycleOwner, Observer { it ->
            if(it){
                homeViewModel.getRandomPetDialog(main_layout, tv_title, imv_dog, tv_fact)
            }
        });

        homeViewModel.show_toast().observe(viewLifecycleOwner, Observer { it ->
            if(it){
                Toast.makeText(context,homeViewModel.getToastText(),Toast.LENGTH_SHORT).show()
            }
        })

        homeViewModel.changePetImg().observe(viewLifecycleOwner, Observer { it ->
            if(it != ""){
                val picasso = Picasso.get()

                picasso
                    .load(it)
                    .into(imv_api_dog, object: com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            imv_api_dog.visibility = View.VISIBLE
                            tv_fact.visibility = View.VISIBLE
                            pb_fact.visibility = View.INVISIBLE
                        }

                        override fun onError(e: java.lang.Exception?) {
                            imv_api_dog.visibility = View.VISIBLE
                            tv_fact.visibility = View.VISIBLE
                            pb_fact.visibility = View.INVISIBLE
                        }
                    })

            }
        })

        imv_next_fact.setOnClickListener() {
            imv_api_dog.visibility = View.INVISIBLE
            makeViewsInvisible(tv_fact, pb_fact)
            homeViewModel.getDogImage(WebService(requireContext(), true), imv_api_dog)
            homeViewModel.getFact(repository,service,tv_fact)
        }

        imv_fav.setOnClickListener(){
            homeViewModel.addFav(requireContext(), repository)
        }

        imv_share.setOnClickListener(){
            val tag = "%23RandomDogFact "
            shareOnTwitter(tag + tv_fact.text.toString())

            homeViewModel.addSharedNum(repository)
        }

        imv_copy.setOnClickListener(){
            val myClipboard: ClipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("fact text", tv_fact.text.toString())

            myClipboard.setPrimaryClip(clip)

            Toast.makeText(requireContext(),resources.getString(R.string.home_text_copied), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareOnTwitter(text:String){
        val tweetUrl = ("https://twitter.com/intent/tweet?text=$text")
        val uri: Uri = Uri.parse(tweetUrl)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun makeViewsInvisible(tv_fact:TextView, pb_fact: ProgressBar){
        tv_fact.text = ""
        tv_fact.visibility = View.INVISIBLE
        pb_fact.visibility = View.VISIBLE
    }
}