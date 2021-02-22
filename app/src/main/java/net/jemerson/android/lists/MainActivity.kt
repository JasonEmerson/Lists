package net.jemerson.android.lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import net.jemerson.android.lists.ItemBankListFragment.Companion.newInstance
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ItemBankListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = ItemBankListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onItemBankSelected(itemBankId: UUID) {
        val fragment = ItemListFragment.newInstance(itemBankId)
        Log.d(TAG, "sending itembank UUID to ItemListFragment:: $itemBankId")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }
}