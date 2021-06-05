package app.rodrigonovoa.dogsrandomfacts.database

import androidx.room.*

@Dao
interface UserDAO {

    @Query("SELECT * FROM user WHERE id = 1")
    suspend fun getUser(): UserModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserModel)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    @Query("UPDATE user SET facts_num = (facts_num+1)  WHERE id = 1")
    suspend fun updateSumFactsNum(): Int

    @Query("UPDATE user SET favfacts_num = (favfacts_num-1)  WHERE id = 1")
    suspend fun updateSubFavsNum()

    @Query("UPDATE user SET favfacts_num = (favfacts_num+1)  WHERE id = 1")
    suspend fun updateSumFavsNum()

    @Query("UPDATE user SET sharedfacts_num = (sharedfacts_num+1)  WHERE id = 1")
    suspend fun updateSumSharedNum()

    @Query("UPDATE user SET opened_num = (opened_num+1)  WHERE id = 1")
    suspend fun updateSumOpenedNum()

    @Query("UPDATE user SET username = :username WHERE id = 1")
    suspend fun updateUsername(username:String)

    @Query("SELECT username FROM user WHERE id = 1")
    suspend fun getUsername(): String

    @Query("SELECT facts_num FROM user WHERE id = 1")
    suspend fun getFactsNum(): Int

    @Query("SELECT favfacts_num FROM user WHERE id = 1")
    suspend fun getFavsNum(): Int

    @Query("SELECT sharedfacts_num FROM user WHERE id = 1")
    suspend fun getSharedNum(): Int

    @Query("SELECT opened_num FROM user WHERE id = 1")
    suspend fun getOpenedNum(): Int
}