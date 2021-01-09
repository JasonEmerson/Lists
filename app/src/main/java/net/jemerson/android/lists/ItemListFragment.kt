package net.jemerson.android.lists

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "ItemListFragment"
private const val REQUEST_ITEM = 0

class ItemListFragment : Fragment(), NameDialogFragment.Callbacks {

    private lateinit var itemListViewModel: ItemListViewModel
    private lateinit var itemRecyclerView: RecyclerView
    private var adapter: ItemAdapter? = ItemAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d(TAG, "------onCreate-----")
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

        itemRecyclerView =
            view.findViewById(R.id.item_recycler_view) as RecyclerView
        itemRecyclerView.layoutManager = LinearLayoutManager(context)
        itemRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemListViewModel.itemListLiveData.observe(
                viewLifecycleOwner,
                Observer { items ->
                    items?.let {
                        Log.i(TAG, "Got items ${items.size}")
                        updateUI(items)
                    }

        })
    }

    private fun updateUI(items: List<Item>) {
        Log.d(TAG, "------updateUI-----")
        adapter = ItemAdapter(items)
        itemRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_item_list, menu)
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

        val item = Item()
        item.title = string.toString()
        if(item.title != "") itemListViewModel.addItem(item)
    }

    private inner class ItemHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var item: Item

        private val titleTextView: TextView = itemView.findViewById(R.id.item_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Item) {
            this.item = item
            titleTextView.text = this.item.title
        }

        override fun onClick(v: View) {
            Toast.makeText(context, "${item.title} pressed!", Toast.LENGTH_SHORT)
                .show()
            itemListViewModel.deleteById(item.id)
        }
    }

    private inner class ItemAdapter(var items: List<Item>)
        : RecyclerView.Adapter<ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : ItemHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val puzzle = items[position]
            holder.bind(puzzle)
        }
    }

    companion object {
        fun newInstance(): ItemListFragment {
            return ItemListFragment()
        }
    }


}