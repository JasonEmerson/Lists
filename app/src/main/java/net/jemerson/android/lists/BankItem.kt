package net.jemerson.android.lists

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

private const val TAG = "BankItem"

@Entity(foreignKeys = [ForeignKey(
        entity = ItemBank::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("ownerId"))])
data class BankItem(@PrimaryKey val itemId: UUID = UUID.randomUUID(),
                    var title: String = "Test Item",
                    var ownerId: UUID? = null)