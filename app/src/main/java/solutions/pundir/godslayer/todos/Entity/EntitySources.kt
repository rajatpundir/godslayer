package solutions.pundir.godslayer.todos.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "SOURCES",
    primaryKeys = arrayOf("MODULE_ID", "ID"),
    indices = arrayOf(Index(value = ["MODULE_ID", "PARENT_ID", "NAME"], unique = true),
    Index(value=["MODULE_ID", "MAGNET_LINK_ID"], unique = true)),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = EntityModules::class,
            parentColumns = arrayOf("ID"),
            childColumns = arrayOf("MODULE_ID")),
        ForeignKey(
            entity = EntityMagnetLinks::class,
            parentColumns = arrayOf("MODULE_ID", "ID"),
            childColumns = arrayOf("MODULE_ID", "MAGNET_LINK_ID")),
        ForeignKey(
            entity = EntityEpisodes::class,
            parentColumns = arrayOf("MODULE_ID", "ID"),
            childColumns = arrayOf("MODULE_ID", "PARENT_ID"))
    )
)
data class EntitySources(

    @ColumnInfo(name = "MODULE_ID")
    val module_id : Int,
    @ColumnInfo(name = "ID")
    val rid : Int,
    @ColumnInfo(name = "MAGNET_LINK_ID")
    val magnet_link_id : Int,
    @ColumnInfo(name = "FILE_NUM")
    val file_num : Int = 0,
    @ColumnInfo(name = "NAME")
    val name : String,
    @ColumnInfo(name = "PARENT_ID")
    val parent_id : Int

)