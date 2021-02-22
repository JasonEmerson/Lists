package net.jemerson.android.lists

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import net.jemerson.android.lists.database.ItemDatabase
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "item-database"
private const val TAG = "ItemRepository"

class ItemRepository private constructor(context: Context) {

    private val database : ItemDatabase = Room.databaseBuilder(
            context.applicationContext,
            ItemDatabase::class.java,
            DATABASE_NAME
    ).build()

    private val itemDao = database.itemDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getItemBanks(): LiveData<List<ItemBank>> {
        Log.d(TAG, "requesting List<ItemBank> from repo: ${itemDao.getItemBanks()}")
        return itemDao.getItemBanks()
    }

    fun getBankItems(bankItemId: UUID): LiveData<List<BankItem>> {
        Log.d(TAG, "requesting List<BankItem> from repo: ${itemDao.getBankItems(bankItemId)}")
        return itemDao.getBankItems(bankItemId)
    }

    fun updateItemBank(itemBank: ItemBank) {
        executor.execute {
            itemDao.updateItemBank(itemBank)
        }
    }

    fun updateBankItem(bankItem: BankItem) {
        executor.execute {
            itemDao.updateBankItem(bankItem)
        }
    }

    fun addItemBank(itemBank: ItemBank) {
        executor.execute {
            itemDao.addItemBank(itemBank)
        }
    }

    fun addBankItem(bankItem: BankItem) {
        executor.execute {
            itemDao.addBankItem(bankItem)
            Log.d(TAG, "BankItem sent to itemDao: ${bankItem.title} - ${bankItem.itemId}")
        }
    }

    fun deleteById(id: UUID) {
        executor.execute {
            itemDao.deleteById(id)
        }
    }

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