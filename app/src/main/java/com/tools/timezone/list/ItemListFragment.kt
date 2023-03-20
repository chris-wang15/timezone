package com.tools.timezone.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.CachedViewModel
import com.tools.timezone.databinding.FragmentItemListBinding
import com.tools.timezone.databinding.ItemListContentBinding
import com.tools.timezone.model.TimeZoneData
import com.tools.timezone.util.DuplicateClickListener

class ItemListFragment : Fragment() {
    companion object {
        private const val TAG = "ItemListFragment"
    }

    private var binding: FragmentItemListBinding? = null
    private val cachedViewModel: CachedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(
            inflater, container, false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            val recyclerView: RecyclerView = it.itemList
            val adapter = SimpleItemRecyclerViewAdapter(arrayListOf(), cachedViewModel)
            recyclerView.adapter = adapter
            cachedViewModel.list.observe(
                viewLifecycleOwner
            ) { data ->
                if (data.isNotEmpty()) {
                    adapter.updateData(data)
                } else {
                    Log.i(TAG, "no cached data found")
                    cachedViewModel.resetCache()
                }
            }

            cachedViewModel.getCachedLists()

            it.resetButton.setOnClickListener(object : DuplicateClickListener() {
                override fun onCLick(v: View?) {
                    cachedViewModel.resetCache()
                }
            })
        }
    }

    class SimpleItemRecyclerViewAdapter(
        private val values: ArrayList<TimeZoneData>,
        private val cachedViewModel: CachedViewModel
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.let {
                it.nameView.text = item.name
                val cityName = if (item.cities.isEmpty()) "UN KNOW" else item.cities[0]
                it.famousCity.text = cityName
                it.followSwitch.isChecked = item.followed
                it.followSwitch.setOnCheckedChangeListener { _, isChecked ->
                    cachedViewModel.updateFollowState(item.id, isChecked)
                    values[position].followed = isChecked
                    notifyItemChanged(position)
                }
                it.itemView.setOnClickListener(
                    object : DuplicateClickListener() {
                        override fun onCLick(v: View?) {
                            Log.i(TAG, "item click: ${v?.findNavController()}")
//                            val bundle = Bundle()
//                            bundle.putInt(
//                                ItemDetailFragment.ARG_ITEM_ID,
//                                item.id
//                            )
//                            this@with.findNavController().navigate(R.id.show_item_detail, bundle)
                        }

                    }
                )
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun updateData(list: List<TimeZoneData>) {
            values.clear()
            values.addAll(list)
            notifyDataSetChanged()
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(binding: ItemListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val nameView: TextView = binding.name
            val famousCity: TextView = binding.mostFamousCity
            val followSwitch: SwitchCompat = binding.followSwitch
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}