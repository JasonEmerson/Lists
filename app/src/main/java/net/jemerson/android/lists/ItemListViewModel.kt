package net.jemerson.android.lists

import androidx.lifecycle.ViewModel
import java.util.*

class ItemListViewModel  : ViewModel() {

    private val itemRepository = ItemRepository.get()
    val itemListLiveData = itemRepository.getItems()

    fun addItem(item: Item) {
        itemRepository.addItem(item)
    }

    fun deleteById(id: UUID) {
        itemRepository.deleteById(id)
    }
}