package com.tools.timezone.presentation.fragment.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.databinding.ItemListContentBinding
import com.tools.timezone.presentation.viewmodel.CachedViewModel
import com.tools.timezone.domain.model.TimeZoneData

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
            val followState = cachedViewModel.getFollowedState(item)
            it.followSwitch.isChecked = followState
            it.followSwitch.setOnCheckedChangeListener { switch, isChecked ->
                if (!switch.isPressed) {
                    // prevent triggered setting isChecked when binding
                    return@setOnCheckedChangeListener
                }
                Log.i(TAG, "setOnCheckedChangeListener $isChecked")
                it.followSwitch.isChecked = isChecked
                cachedViewModel.updateFollowState(item.id, isChecked)
            }
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