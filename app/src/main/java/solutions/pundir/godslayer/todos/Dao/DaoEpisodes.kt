package solutions.pundir.godslayer.todos.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solutions.pundir.godslayer.todos.Entity.EntityEpisodes

@Dao
interface DaoEpisodes {

    @Query("SELECT * from EPISODES ORDER BY ID DESC")
    fun getAll(): LiveData<List<EntityEpisodes>>

    @Query("SELECT * FROM EPISODES WHERE MODULE_ID = :module_id AND ID = :rid LIMIT 1")
    fun get_record_by_id(rid: String, module_id : Int): EntityEpisodes

    @Query("SELECT * FROM EPISODES WHERE MODULE_ID = :module_id AND NAME = :name")
    fun get_record_by_name(name: String, module_id: Int): EntityEpisodes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: EntityEpisodes)

}