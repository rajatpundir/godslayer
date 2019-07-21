package solutions.pundir.godslayer.todos.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "LANGUAGES",
    primaryKeys = arrayOf("MODULE_ID", "ID"),
    indices = arrayOf(Index(value = ["MODULE_ID", "NAME"], unique = true)),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = EntityModules::class,
            parentColumns = arrayOf("ID"),
            childColumns = arrayOf("MODULE_ID"))
    )
)
data class EntityLanguages(

    @ColumnInfo(name = "MODULE_ID")
    val module_id : Int,
    @ColumnInfo(name = "ID")
    val rid : Int,
    @ColumnInfo(name = "NAME")
    val name : String

)