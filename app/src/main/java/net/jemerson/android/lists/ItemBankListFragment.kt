package net.jemerson.android.lists

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ItemBankListFragment"
private const val REQUEST_ITEM = 0

class ItemBankListFragment : Fragment(), NameDialogFragment.Callbacks {

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun onItemBankSelected(ItemBankId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var addImageButton: ImageButton
    private lateinit var itemBankListViewModel: ItemBankListViewModel
    private lateinit var itemBankRecyclerView: RecyclerView
    private var adapter: ItemBankAdapter? = ItemBankAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d(TAG, "------onCreate-----")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
        itemBankListViewModel = ViewModelProvider(this).get(ItemBankListViewModel::class.java)
        Log.d(TAG, "------onAttach-----")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_itembank_list, container, false)

        addImageButton = view.findViewById(R.id.add_button) as ImageButton

        addImageButton.setOnClickListener {
            createDialog()
        }

        itemBankRecyclerView =
            view.findViewById(R.id.itembank_recycler_view) as RecyclerView
        itemBankRecyclerView.layoutManager = LinearLayoutManager(context)
        itemBankRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemBankListViewModel.itemBankListLiveData.observe(
                viewLifecycleOwner,
                { itemBanks ->
                    itemBanks?.let {
                        Log.d(TAG, "Got items ${itemBanks.size}")
                        updateUI(itemBanks)
                    }
        })
    }

    private fun updateUI(items: List<ItemBank>) {
        Log.d(TAG, "------updateUI-----")
        adapter = ItemBankAdapter(items)
        itemBankRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_itembank_list, menu)
    }

    override fun onOptionsItemSelected(menuitem: MenuItem): Boolean {
        return when (menuitem.itemId) {
            R.id.add_item -> {
                createDialog()
                true
            }
            else -> return super.onOptionsItemSelected(menuitem)
        }
    }

    private fun createDialog() {
        val nameDialogFragment = NameDialogFragment()
        nameDialogFragment.setTargetFragment(this, REQUEST_ITEM)
        nameDialogFragment.show(parentFragmentManager, null)
    }

    override fun onItemTextEntered(string: Editable) {

        //TODO move logic to repository

        val itemBank = ItemBank()
        itemBank.title = string.toString()
        if(itemBank.title != "") itemBankListViewModel.addItemBank(itemBank)
        Log.d(TAG, "--------------itemBank.title=${itemBank.title}")
    }

    private inner class ItemBankHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var itemBank: ItemBank

        private val titleTextView: TextView = itemView.findViewById(R.id.itembank_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(itemBank: ItemBank) {
            this.itemBank = itemBank
            titleTextView.text = this.itemBank.title
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${itemBank.title} pressed!", Toast.LENGTH_SHORT)
                .show()
            //itemBankListViewModel.deleteById(itemBank.id)
            callbacks?.onItemBankSelected(itemBank.id)
        }
    }

    private inner class ItemBankAdapter(var itemBanks: List<ItemBank>)
        : RecyclerView.Adapter<ItemBankHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ItemBankHolder {
            val view = layoutInflater.inflate(R.layout.list_itembank, parent, false)
            return ItemBankHolder(view)
        }

        override fun getItemCount() = itemBanks.size

        override fun onBindViewHolder(bankHolder: ItemBankHolder, position: Int) {
            val itemBank = itemBanks[position]
            bankHolder.bind(itemBank)
        }
    }

    companion object {
        fun newInstance(): ItemBankListFragment {
            return ItemBankListFragment()
        }
    }
}