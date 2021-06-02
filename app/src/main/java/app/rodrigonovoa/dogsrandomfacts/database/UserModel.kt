package app.rodrigonovoa.dogsrandomfacts.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserModel(@PrimaryKey(autoGenerate = true) val id: Int, val username: String, val facts_num:Int, val favfacts_num:Int, val sharedfacts_num:Int, val opened_num:Int)