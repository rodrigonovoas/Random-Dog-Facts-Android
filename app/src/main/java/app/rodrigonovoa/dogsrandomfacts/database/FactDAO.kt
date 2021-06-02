package app.rodrigonovoa.dogsrandomfacts.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FactDAO {

    @Query("SELECT * FROM fact ORDER BY id ASC")
    fun getFacts(): List<FactModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFact(fact: FactModel):Long

    @Query("DELETE FROM fact")
    suspend fun deleteAllFacts()
}