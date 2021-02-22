package net.jemerson.android.lists

import androidx.lifecycle.ViewModel
import java.util.*

class ItemBankListViewModel  : ViewModel() {

    private val itemRepository = ItemRepository.get()
    val itemBankListLiveData = itemRepository.getItemBanks()

    fun addItemBank(itemBank: ItemBank) {
        itemRepository.addItemBank(itemBank)
    }

    fun deleteById(id: UUID) {
        itemRepository.deleteById(id)
    }
}