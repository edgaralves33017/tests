package com.project.test.ui.listresult.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.test.data.models.Entry
import com.project.test.data.util.ApiResult
import com.project.test.databinding.FragmentListresultBinding
import com.project.test.ui.listresult.adapter.ListResultListAdapter
import com.project.test.ui.listresult.viewmodel.ListResultViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ListResultFragment : Fragment(),
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentListresultBinding? = null

    /**
     * Instance of [ListResultViewModel]
     */
    private val viewModel: ListResultViewModel by viewModels()

    /**
     * List that contains the entry list from the api response
     */
    private var publicfeedList: MutableList<Entry> = mutableListOf()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListresultBinding.inflate(inflater, container, false)
        setRecyclerView()
        setObservers()
        setSwipeRefreshView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Click listener that is fired when the user clicks on the image.
     */
    private val imageClickListener = object : ListResultListAdapter.ItemClickListener {
        override fun onItemClick(view: View?, position: Int, imageView: ImageView) {
            transitionToListFragment(publicfeedList[position], imageView)
        }
    }

    override fun onRefresh() {
        GlobalScope.async {
            viewModel.getEntries()
        }
    }

    /**
     * Sets up the recycler view with GridLayoutManager
     */
    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val adapter = ListResultListAdapter(requireContext(), publicfeedList)
        adapter.setClickListeners(imageClickListener)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Sets the SwipeRefreshView so that the refresh is triggered when the view is created.
     */
    private fun setSwipeRefreshView() {
        binding.refreshLayout.setOnRefreshListener(this)
        if (publicfeedList.isEmpty()) {
            binding.refreshLayout.post {
                binding.refreshLayout.isRefreshing = true
                this.onRefresh()
            }
        }
    }

    /**
     * Sets the observer on [ListResultViewModel] so we can observe the state of the api calls.
     */
    private fun setObservers() {
        viewModel.loadState.observe(
            requireActivity()
        ) {
            when (it) {
                is ApiResult.Loading -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                }

                is ApiResult.Failure -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = it.message ?: ""
                    binding.refreshLayout.isRefreshing = false
                }

                is ApiResult.Success -> {
                    binding.refreshLayout.isRefreshing = false
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                    publicfeedList.clear()
                    publicfeedList.addAll(it.data!!)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
    }

    /**
     * Method that transitions to the next fragment on the app. Uses shared elements to animate the transition
     */
    private fun transitionToListFragment(entry: Entry, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(imageView to entry.media.m)
        val action =
            ListResultFragmentDirections.actionListResultFragmentToImageDetailFragment(entry)
        findNavController().navigate(action, extras)
    }
}