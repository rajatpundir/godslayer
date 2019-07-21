package solutions.pundir.godslayer.todos.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solutions.pundir.godslayer.todos.Entity.EntityModules

@Dao
interface DaoModules {

    @Query("SELECT * from MODULES ORDER BY ID DESC")
    fun getAll(): LiveData<List<EntityModules>>

    @Query("SELECT * FROM MODULES WHERE ID = :rid")
    fun get_record_by_id(rid: String): EntityModules

    @Query("SELECT * FROM MODULES WHERE ID = :name")
    fun get_record_by_name(name: String): EntityModules

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: EntityModules)

}