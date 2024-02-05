package com.tools.timezone.presentation.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tools.timezone.databinding.FragmentItemDetailBinding
import com.tools.timezone.presentation.viewmodel.CachedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {

    private var binding: FragmentItemDetailBinding? = null
    private val detailViewModel: DetailViewModel by viewModels()
    private val cachedViewModel: CachedViewModel by activityViewModels()
    private var itemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                itemId = it.getInt(ARG_ITEM_ID)
                detailViewModel.setDetailData(itemId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding!!.root

        Log.i(TAG, "onCreate ${cachedViewModel.list.value?.get(itemId)}")
        bindData()
        detailViewModel.detailData.observe(
            viewLifecycleOwner
        ) {
            Log.i(TAG, "detailViewModel.liveData.observe $it")
            bindData()
        }

        return rootView
    }

    private fun bindData() {
        val item = detailViewModel.detailData.value
        item ?: return
        binding?.apply {
            tzName.text = item.name
            timeDetail.timeZone = item.name
            dataDetail.timeZone = item.name
        }
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
        private const val TAG = "ItemDetailFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}