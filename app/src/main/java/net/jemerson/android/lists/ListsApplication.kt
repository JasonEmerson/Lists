package net.jemerson.android.lists

import android.app.Application

class ListsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ItemRepository.initialize(this)
    }
}