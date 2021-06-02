package app.rodrigonovoa.dogsrandomfacts.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact")
class FactModel(@PrimaryKey(autoGenerate = true) val id: Int, val fact: String)
