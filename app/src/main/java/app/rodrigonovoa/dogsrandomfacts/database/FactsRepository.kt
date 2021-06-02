package app.rodrigonovoa.dogsrandomfacts.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.lifecycleScope
import app.rodrigonovoa.dogsrandomfacts.model.Fact

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FactsRepository(factsDB:FactRoomDb) {

    private var factDAO: FactDAO
    private var favDAO: FavDAO
    private var userDAO:UserDAO

    init{
        factDAO = factsDB.factDao()
        favDAO = factsDB.favDao()
        userDAO = factsDB.userDao()
    }

    //FACTS

    @WorkerThread
    suspend fun insertFact(fact: FactModel):Long {
        val id = factDAO.insertFact(fact)

        return id
    }

    @WorkerThread
    suspend fun getFacts():List<FactModel> {
        return factDAO.getFacts()
    }

    //FAVS

    @WorkerThread
    suspend fun insertFav(fav: FavModel) {
        favDAO.insertFav(fav)
    }

    @WorkerThread
    suspend fun getFavs():List<FavModel> {
        return favDAO.getFavs()
    }

    @WorkerThread
    suspend fun deleteFavById(id:Int) {
        return favDAO.deleteFavById(id)
    }

    @WorkerThread
    suspend fun getFavByFactId(id:Int):List<FavModel> {
        return favDAO.getFavByFactId(id)
    }

    //USER

    @WorkerThread
    suspend fun insertUser(user:UserModel) {
        userDAO.insertUser(user)
    }

    @WorkerThread
    suspend fun updateUsername(value:String) {
        userDAO.updateUsername(value)
    }

    @WorkerThread
    suspend fun addFactNum() {
        userDAO.updateSumFactsNum()
    }

    @WorkerThread
    suspend fun addFavNum() {
        userDAO.updateSumFavsNum()
    }

    @WorkerThread
    suspend fun subFavNum() {
        userDAO.updateSubFavsNum()
    }

    @WorkerThread
    suspend fun addSharedNum() {
        userDAO.updateSumSharedNum()
    }

    @WorkerThread
    suspend fun addOpenedNum() {
       userDAO.updateSumOpenedNum()
    }

    @WorkerThread
    suspend fun getUsername():String {
        return userDAO.getUsername()
    }

    @WorkerThread
    suspend fun getFactsNum():Int {
        return userDAO.getFactsNum()
    }

    @WorkerThread
    suspend fun getFavsNum():Int {
        return userDAO.getFavsNum()
    }

    @WorkerThread
    suspend fun getOpenedNum():Int {
        return userDAO.getOpenedNum()
    }

    @WorkerThread
    suspend fun getSharedNum():Int {
        return userDAO.getSharedNum()
    }

}