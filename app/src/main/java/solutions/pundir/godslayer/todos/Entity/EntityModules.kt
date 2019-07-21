package solutions.pundir.godslayer.todos.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "MODULES",
    indices = arrayOf(Index(value = ["NAME"], unique = true))
)
data class EntityModules(

    @PrimaryKey
    @ColumnInfo(name = "ID")
    val rid : Int,
    @ColumnInfo(name = "NAME")
    val name : String

)