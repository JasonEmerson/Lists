package net.jemerson.android.lists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.util.*

private const val TAG = "ItemListViewModel"

class ItemListViewModel  : ViewModel() {

    private val itemRepository = ItemRepository.get()
    //val bankItemListLiveData = itemRepository.getBankItems(UUID)

    fun addBankItem(bankItem: BankItem) {
        itemRepository.addBankItem(bankItem)
    }

    fun deleteById(id: UUID) {
        itemRepository.deleteById(id)
    }

    fun getBankItemListLiveData(bankItemId: UUID): LiveData<List<BankItem>> {
        Log.d(TAG, "Sending UUID to repo: $bankItemId")
        return itemRepository.getBankItems(bankItemId)
    }
}