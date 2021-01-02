package net.jemerson.android.lists

import androidx.lifecycle.ViewModel

class ItemListViewModel  : ViewModel() {

    val items = mutableListOf<Item>()

    init {
        for (i in 0 until 20) {
            val item = Item()
            item.title = "Item #$i"
            items += item
        }
    }
}