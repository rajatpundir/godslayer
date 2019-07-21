package solutions.pundir.godslayer.todos.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "PUBLISHERS",
    primaryKeys = arrayOf("MODULE_ID", "ID"),
    indices = arrayOf(Index(value = ["MODULE_ID", "PARENT_ID", "NAME"], unique = true)),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = EntityModules::class,
            parentColumns = arrayOf("ID"),
            childColumns = arrayOf("MODULE_ID")),
        ForeignKey(
            entity = EntityPlatforms::class,
            parentColumns = arrayOf("MODULE_ID", "ID"),
            childColumns = arrayOf("MODULE_ID", "PARENT_ID"))
    )
)
data class EntityPublishers(

    @ColumnInfo(name = "MODULE_ID")
    val module_id : Int,
    @ColumnInfo(name = "ID")
    val rid : Int,
    @ColumnInfo(name = "IMAGE_URL")
    val image_url : String?,
    @ColumnInfo(name = "NAME")
    val name : String,
    @ColumnInfo(name = "PARENT_ID")
    val parent_id : Int

)