package solutions.pundir.godslayer.todos.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solutions.pundir.godslayer.todos.Entity.EntitySources

@Dao
interface DaoSources {

    @Query("SELECT * from SOURCES ORDER BY ID DESC")
    fun getAll(): LiveData<List<EntitySources>>

    @Query("SELECT * FROM SOURCES WHERE MODULE_ID = :module_id AND ID = :rid LIMIT 1")
    fun get_record_by_id(rid: String, module_id : Int): EntitySources

    @Query("SELECT * FROM SOURCES WHERE MODULE_ID = :module_id AND NAME = :name")
    fun get_record_by_name(name: String, module_id: Int): EntitySources

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: EntitySources)

}