package net.jemerson.android.lists.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import net.jemerson.android.lists.BankItem
import net.jemerson.android.lists.ItemBank

import java.util.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM itemBank")
    fun getItemBanks(): LiveData<List<ItemBank>>

    @Query("SELECT * FROM bankItem WHERE ownerId=(:ownerId)")
    fun getBankItems(ownerId: UUID): LiveData<List<BankItem>>

    @Query("DELETE FROM itemBank WHERE id=(:id)")
    fun deleteById(id: UUID)

    @Update
    fun updateItemBank(itemBank: ItemBank)

    @Update
    fun updateBankItem(bankItem: BankItem)

    @Insert
    fun addItemBank(itemBank: ItemBank)

    @Insert
    fun addBankItem(bankItem: BankItem)
}