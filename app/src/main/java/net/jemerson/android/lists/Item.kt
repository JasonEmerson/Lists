package net.jemerson.android.lists

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

private const val TAG = "Item"

@Entity
data class Item(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String = "", )