package net.jemerson.android.lists.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.jemerson.android.lists.Item

@Database(entities = [ Item::class ], version=1)
@TypeConverters(ItemTypeConverters::class)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}