package net.jemerson.android.lists

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import net.jemerson.android.lists.database.ItemDatabase
import java.util.*

private const val DATABASE_NAME = "item-database"

class ItemRepository private constructor(context: Context) {

    private val database : ItemDatabase = Room.databaseBuilder(
            context.applicationContext,
            ItemDatabase::class.java,
            DATABASE_NAME
    ).build()

    private val itemDao = database.itemDao()

    fun getItems(): LiveData<List<Item>> = itemDao.getItems()

    fun getItem(id: UUID): LiveData<Item?> = itemDao.getItem(id)

    companion object {
        private var INSTANCE: ItemRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ItemRepository(context)
            }
        }

        fun get(): ItemRepository {
            return INSTANCE ?:
            throw IllegalStateException("ItemRepository must be initialized")
        }
    }
}