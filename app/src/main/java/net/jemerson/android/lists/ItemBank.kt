package net.jemerson.android.lists

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

private const val TAG = "ItemBank"

@Entity
data class ItemBank(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var title: String = "Test Bank", )