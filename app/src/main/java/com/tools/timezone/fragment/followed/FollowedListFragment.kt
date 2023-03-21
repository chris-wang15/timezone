package com.tools.timezone.fragment.followed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.R
import com.tools.timezone.databinding.FragmentFollowedListBinding
import com.tools.timezone.util.DuplicateClickListener

class FollowedListFragment : Fragment() {

    private var binding: FragmentFollowedListBinding? = null
    private val followedViewModel: FollowedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowedListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            val recyclerView: RecyclerView = it.followedItemList
            val adapter = FollowedViewAdapter(arrayListOf())
            recyclerView.adapter = adapter
            followedViewModel.list.observe(
                viewLifecycleOwner
            ) { data ->
                if (data.isNotEmpty()) {
                    recyclerView.visibility = View.VISIBLE
                    it.noResult.visibility = View.GONE
                    adapter.updateData(data)
                } else {
                    it.noResult.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }

            followedViewModel.getFollowedLists()

            it.fab.setOnClickListener(object : DuplicateClickListener(){
                override fun onCLick(v: View?) {
                    jumpToListFragment(v)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun jumpToListFragment(view: View?) {
        view ?: return
        val bundle = Bundle()
//        bundle.putString( TODO 添加当前时区
//            ItemDetailFragment.ARG_ITEM_ID,
//            item.id
//        )
        view.findNavController().navigate(R.id.show_item_list, bundle)
    }
}