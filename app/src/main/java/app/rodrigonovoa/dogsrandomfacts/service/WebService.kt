package app.rodrigonovoa.dogsrandomfacts.service

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebService {
    private var BASE_URL = "http://dog-api.kinduff.com/api/"
    private var instance:WebService? = null
    private val retrofit: Retrofit
    private var mContext: Context? = null

    constructor(context: Context, imageApi:Boolean=false){
        mContext = context

        if(imageApi){
            BASE_URL = "https://dog.ceo/api/"
        }

        retrofit = Retrofit.Builder().
        baseUrl(BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).
        build()
    }

    fun getInstance():WebService{
        if (instance == null){
            instance = WebService(this!!.mContext!!)
        }
        return instance as WebService
    }

    fun createWebService():WebService{
        return retrofit.create(WebService::class.java)
    }

    fun returnClientInterface():DogFactsService{
        return retrofit.create(DogFactsService::class.java)
    }

}