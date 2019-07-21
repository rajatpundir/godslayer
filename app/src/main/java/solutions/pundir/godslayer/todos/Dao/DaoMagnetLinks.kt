package solutions.pundir.godslayer.todos.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solutions.pundir.godslayer.todos.Entity.EntityMagnetLinks

@Dao
interface DaoMagnetLinks {

    @Query("SELECT * from MAGNET_LINKS ORDER BY ID DESC")
    fun getAll(): LiveData<List<EntityMagnetLinks>>

    @Query("SELECT * FROM MAGNET_LINKS WHERE MODULE_ID = :module_id AND ID = :rid LIMIT 1")
    fun get_record_by_id(rid: String, module_id : Int): EntityMagnetLinks

    @Query("SELECT * FROM MAGNET_LINKS WHERE MODULE_ID = :module_id AND MAGNET_URL = :magnet_url")
    fun get_record_by_name(magnet_url: String, module_id: Int): EntityMagnetLinks

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: EntityMagnetLinks)

}