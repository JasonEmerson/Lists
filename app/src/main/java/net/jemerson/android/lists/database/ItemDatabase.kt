package net.jemerson.android.lists.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.jemerson.android.lists.BankItem
import net.jemerson.android.lists.ItemBank

@Database(entities = [ItemBank::class, BankItem::class], version = 1)
@TypeConverters(ItemTypeConverters::class)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}