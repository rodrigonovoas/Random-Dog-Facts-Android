package app.rodrigonovoa.dogsrandomfacts.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.rodrigonovoa.dogsrandomfacts.model.Fact

@Dao
interface FavDAO {

    @Query("SELECT * FROM fav ORDER BY id ASC")
    fun getFavs(): List<FavModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFav(fav: FavModel)

    @Query("DELETE FROM fav")
    suspend fun deleteAllFavs()

    @Query("DELETE FROM fav where id = :id")
    suspend fun deleteFavById(id:Int)

    @Query("SELECT * FROM fav where factid = :id")
    suspend fun getFavByFactId(id:Int): List<FavModel>
}