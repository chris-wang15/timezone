package com.tools.timezone.followed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.tools.timezone.databinding.FragmentFollowedListBinding
import com.tools.timezone.databinding.FragmentItemListBinding
import com.tools.timezone.placeholder.PlaceholderContent

class FollowedListFragment : Fragment() {

    private var _binding: FragmentFollowedListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFollowedListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = binding.followedItemList
        recyclerView.adapter = FollowedViewAdapter(PlaceholderContent.ITEMS)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}