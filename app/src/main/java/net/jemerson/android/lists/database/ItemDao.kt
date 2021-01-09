package net.jemerson.android.lists.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import net.jemerson.android.lists.Item
import java.util.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun getItems(): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE id=(:id)")
    fun getItem(id: UUID): LiveData<Item?>

    @Query("DELETE FROM item WHERE id=(:id)")
    fun deleteById(id: UUID)

    @Update
    fun updateItem(item: Item)

    @Insert
    fun addItem(item: Item)
}