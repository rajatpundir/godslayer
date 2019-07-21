package solutions.pundir.godslayer.todos.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solutions.pundir.godslayer.todos.Entity.EntityPlatforms

@Dao
interface DaoPlatforms {

    @Query("SELECT * from PLATFORMS ORDER BY ID DESC")
    fun getAll(): LiveData<List<EntityPlatforms>>

    @Query("SELECT * FROM PLATFORMS WHERE MODULE_ID = :module_id AND ID = :rid LIMIT 1")
    fun get_record_by_id(rid: String, module_id : Int): EntityPlatforms

    @Query("SELECT * FROM PLATFORMS WHERE MODULE_ID = :module_id AND NAME = :name")
    fun get_record_by_name(name: String, module_id: Int): EntityPlatforms

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: EntityPlatforms)

}