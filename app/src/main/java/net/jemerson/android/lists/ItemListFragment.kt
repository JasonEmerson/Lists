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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ItemListFragment"
private const val ARG_ITEMBANK_ID = "item_id"
private const val REQUEST_ITEM = 0

class ItemListFragment : Fragment(), NameDialogFragment.Callbacks {

    private lateinit var itemBankId: UUID
    private lateinit var addImageButton: ImageButton
    private lateinit var itemListViewModel: ItemListViewModel
    private lateinit var itemRecyclerView: RecyclerView
    private var adapter: ItemAdapter? = ItemAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemBankId = arguments?.getSerializable(ARG_ITEMBANK_ID) as UUID
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemListViewModel = ViewModelProvider(this).get(ItemListViewModel::class.java)
        Log.d(TAG, "------onAttach-----")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        addImageButton = view.findViewById(R.id.add_button) as ImageButton

        addImageButton.setOnClickListener {
            createDialog()
        }

        itemRecyclerView =
                view.findViewById(R.id.item_recycler_view) as RecyclerView
        itemRecyclerView.layoutManager = LinearLayoutManager(context)
        itemRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemListViewModel.getBankItemListLiveData(itemBankId).observe(
                viewLifecycleOwner,
                { bankItems ->
                    bankItems?.let {
                        Log.d(TAG, "FROM OBSERVER:: size:  ${bankItems.size}")
                        updateUI(bankItems)
                    }
                })
    }

    private fun updateUI(items: List<BankItem>) {
        Log.d(TAG, "------updateUI-----")
        adapter = ItemAdapter(items)
        itemRecyclerView.adapter = adapter
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

        val bankItem = BankItem()
        bankItem.title = string.toString()
        bankItem.ownerId = itemBankId
        if(bankItem.title != "") itemListViewModel.addBankItem(bankItem)
        Log.d(TAG, "created from dialogfrag: bankItem.title=${bankItem.title} UUID: ${bankItem.itemId}")
    }

    private inner class ItemHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var bankItem: BankItem

        private val titleTextView: TextView = itemView.findViewById(R.id.itembank_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(bankItem: BankItem) {
            this.bankItem = bankItem
            titleTextView.text = this.bankItem.title
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${bankItem.title} pressed!", Toast.LENGTH_SHORT)
                    .show()
            //itemListViewModel.deleteById(item.itemId)
        }
    }

    private inner class ItemAdapter(var bankItems: List<BankItem>)
        : RecyclerView.Adapter<ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ItemHolder {
            val view = layoutInflater.inflate(R.layout.list_itembank, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount() = bankItems.size

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val bankItem = bankItems[position]
            holder.bind(bankItem)
        }
    }

    companion object {
        fun newInstance(itemBankId: UUID): ItemListFragment {
            val args = Bundle().apply {
                putSerializable(ARG_ITEMBANK_ID, itemBankId)
            }
            return ItemListFragment().apply {
                arguments = args
            }
        }
    }
}