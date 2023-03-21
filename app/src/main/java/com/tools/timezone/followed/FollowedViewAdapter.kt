package com.tools.timezone.followed

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextClock
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.databinding.FollowedListContentBinding
import com.tools.timezone.model.TimeZoneData
import java.util.*

class FollowedViewAdapter(
    private val values: ArrayList<TimeZoneData>
) : RecyclerView.Adapter<FollowedViewAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "FollowedViewAdapter"
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<TimeZoneData>) {
        values.clear()
        values.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeData(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            FollowedListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tzView.text = item.name
        val offsetMs = item.zone * 60 * 60 * 1000
        Log.i(TAG, "${item.name} zone: $offsetMs at $position")
        holder.timeDetail.timeZone = SimpleTimeZone(offsetMs, item.name).displayName
        holder.dataDetail.timeZone = SimpleTimeZone(offsetMs, item.name).displayName
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: FollowedListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tzView: TextView = binding.curTz
        val timeDetail: TextClock = binding.timeDetail
        val dataDetail: TextClock = binding.dataDetail
    }
}