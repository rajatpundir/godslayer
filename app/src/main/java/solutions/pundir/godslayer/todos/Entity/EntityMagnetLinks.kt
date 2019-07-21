package solutions.pundir.godslayer.todos.Entity

import androidx.room.*

@Entity(
    tableName = "MAGNET_LINKS",
    primaryKeys = arrayOf("MODULE_ID", "ID"),
    indices = arrayOf(Index(value = ["MODULE_ID", "MAGNET_URL"], unique = true)),
    foreignKeys = arrayOf(
        ForeignKey(
        entity = EntityModules::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("MODULE_ID"))
    )
)
data class EntityMagnetLinks(

    @ColumnInfo(name = "MODULE_ID")
    val module_id : Int,
    @ColumnInfo(name = "ID")
    val rid : Int,
    @ColumnInfo(name = "MAGNET_URL")
    val magnet_url : String

)