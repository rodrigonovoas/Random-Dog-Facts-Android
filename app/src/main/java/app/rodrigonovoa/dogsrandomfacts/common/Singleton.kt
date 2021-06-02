package app.rodrigonovoa.dogsrandomfacts.common

import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import app.rodrigonovoa.dogsrandomfacts.model.Fact

object Singleton {

    private lateinit var repository: FactsRepository

    fun setRepository(repository: FactsRepository){
        this.repository = repository
    }

    fun getRepository():FactsRepository{
        return this.repository
    }
}