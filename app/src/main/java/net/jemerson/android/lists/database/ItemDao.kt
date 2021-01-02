package net.jemerson.android.lists.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import net.jemerson.android.lists.Item
import java.util.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM item")
    fun getItems(): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE id=(:id)")
    fun getItem(id: UUID): LiveData<Item?>
}