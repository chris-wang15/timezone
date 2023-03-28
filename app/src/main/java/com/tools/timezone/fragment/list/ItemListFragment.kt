package com.tools.timezone.fragment.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.databinding.FragmentItemListBinding
import com.tools.timezone.model.CachedViewModel
import com.tools.timezone.util.DuplicateClickListener

class ItemListFragment : Fragment() {
    companion object {
        private const val TAG = "ItemListFragment"
    }

    private var binding: FragmentItemListBinding? = null
    private val cachedViewModel: CachedViewModel by activityViewModels()
    private var retryIfCacheFailed = false

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
            val adapter = ZonesListAdapter(arrayListOf(), cachedViewModel)
            recyclerView.adapter = adapter
            cachedViewModel.list.observe(
                viewLifecycleOwner
            ) { data ->
                if (data.isNotEmpty()) {
                    adapter.updateData(data)
                } else if (retryIfCacheFailed) {
                    Log.i(TAG, "no cached data found")
                    retryIfCacheFailed = false
                    cachedViewModel.resetCache()
                }
            }

            retryIfCacheFailed = true
            cachedViewModel.getCachedLists()

            var text: String? = null
            it.itemSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    text = editable?.toString()
                    it.searchButton.isEnabled = editable != null && editable.isNotEmpty()
                }
            })

            it.searchButton.setOnClickListener(object : DuplicateClickListener() {
                override fun onCLick(v: View?) {
                    cachedViewModel.searchTimeZone(text ?: "")
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}