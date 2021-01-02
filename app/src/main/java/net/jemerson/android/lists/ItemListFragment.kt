package net.jemerson.android.lists

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "ItemListFragment"

class ItemListFragment : Fragment() {

    private lateinit var itemListViewModel: ItemListViewModel
    private lateinit var itemRecyclerView: RecyclerView
    private var adapter: ItemAdapter? = null

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

        updateUI()

        return view
    }

    private fun updateUI() {
        Log.d(TAG, "------updateUI in onCreateView-----")
        val items = itemListViewModel.items
        adapter = ItemAdapter(items)
        itemRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_item_list, menu)
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