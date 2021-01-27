package net.jemerson.android.lists

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment


class NameDialogFragment : DialogFragment() {

    interface Callbacks {
        fun onItemTextEntered(string: Editable)
    }

    private lateinit var itemText: EditText
    private lateinit var addButton: Button

    private fun View.showKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_dialog, container, false)

        itemText = view.findViewById(R.id.item_text) as EditText
        itemText.hint = "Add an item..."
        view.showKeyboard()

        addButton = view.findViewById(R.id.add_button) as Button
        addButton.setOnClickListener {
            Log.d(TAG, "----------------${itemText.text}----------------")
            targetFragment?.let {
                (it as Callbacks).onItemTextEntered(itemText.text)
            }
            view.hideKeyboard()
            dismiss()
        }
        return view
    }

    companion object {
        const val TAG = "ItemConfirmationDialog"
    }
}