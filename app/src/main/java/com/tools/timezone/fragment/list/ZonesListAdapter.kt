package com.tools.timezone.fragment.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.databinding.ItemListContentBinding
import com.tools.timezone.model.CachedViewModel
import com.tools.timezone.model.TimeZoneData

class ZonesListAdapter(
    private val values: ArrayList<TimeZoneData>,
    private val cachedViewModel: CachedViewModel
) : RecyclerView.Adapter<ZonesListAdapter.ViewHolder>() {

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
            it.followSwitch.isChecked = cachedViewModel.followed.value!!.contains(item)
//            cachedViewModel.followed.observe(it.itemView.context as LifecycleOwner) { set ->
//                it.followSwitch.isChecked = set.contains(item)
//            }
            it.followSwitch.setOnCheckedChangeListener { switch, isChecked ->
                if (!switch.isPressed) {
                    // prevent triggered setting isChecked when binding
                    return@setOnCheckedChangeListener
                }
                Log.i(TAG, "setOnCheckedChangeListener $isChecked")
                it.followSwitch.isChecked = isChecked
                cachedViewModel.updateFollowState(item.id, isChecked)
//                notifyItemChanged(position)
            }
//            it.itemView.setOnClickListener(
//                object : DuplicateClickListener() {
//                    override fun onCLick(v: View?) {
//                        Log.i(TAG, "item click")
//                            val bundle = Bundle()
//                            bundle.putInt(
//                                ItemDetailFragment.ARG_ITEM_ID,
//                                item.id
//                            )
//                            v?.findNavController()?.navigate(R.id.show_item_detail, bundle)
//                    }
//
//                }
//            )
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
        val followSwitch: SwitchCompat = binding.followSwitch
    }

    companion object {
        private const val TAG = "ZonesListAdapter"
    }
}