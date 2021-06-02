package app.rodrigonovoa.dogsrandomfacts.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "fav", foreignKeys = arrayOf(
    ForeignKey(entity = FactModel::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("factid"),
    onDelete = ForeignKey.CASCADE),

    ForeignKey(entity = UserModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userid"),
        onDelete = ForeignKey.CASCADE)
))

class FavModel(@PrimaryKey(autoGenerate = true) val id: Int, val factid: Int, val userid:Int)
