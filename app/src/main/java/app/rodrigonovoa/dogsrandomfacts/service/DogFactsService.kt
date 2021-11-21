package app.rodrigonovoa.dogsrandomfacts.service

import app.rodrigonovoa.dogsrandomfacts.model.DogImage
import app.rodrigonovoa.dogsrandomfacts.model.Fact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogFactsService {
    @GET("facts?number={number}")
    fun getFactByQuantity(@Path("number") num:Int): Call<Fact>

    @GET("facts?number=1")
    fun getSingleRandomFact(): Call<Fact>

    @GET("breeds/image/random")
    fun getImageFromApi(): Call<DogImage>
}