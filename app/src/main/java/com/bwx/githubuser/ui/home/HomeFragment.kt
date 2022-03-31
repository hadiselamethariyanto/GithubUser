package com.bwx.githubuser.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.R
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.databinding.FragmentHomeBinding
import com.bwx.githubuser.domain.model.User
import com.bwx.githubuser.paging.asMergedLoadStates
import com.bwx.githubuser.ui.detail_user.DetailUserFragment.Companion.USER_KEY
import com.bwx.githubuser.ui.main.MainAdapter
import com.bwx.githubuser.ui.main.MainLoadStateAdapter
import com.bwx.githubuser.ui.main.MainViewModel
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapter: MainAdapter
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupAdapter()
        initSwipeToRefresh()
        return binding.root
    }

    private fun setupAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mainAdapter = MainAdapter()
        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
//                mainViewModel.getDetailUser(data.login)
//                    .observe(viewLifecycleOwner, detailUserObserver)
                val bundle = bundleOf(USER_KEY to data.login)
                findNavController().navigate(R.id.action_home_to_detail_user, bundle)
            }
        })

        binding.rvUser.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter.withLoadStateHeaderAndFooter(
                header = MainLoadStateAdapter(mainAdapter),
                footer = MainLoadStateAdapter(mainAdapter)
            )
        }

        lifecycleScope.launchWhenCreated {
            mainAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading

                if (loadStates.mediator?.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached) {
                    binding.rvUser.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.GONE
                    binding.rvUser.visibility = View.VISIBLE
                }
            }
        }

        mainViewModel.getUsers().observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                mainAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            mainAdapter.loadStateFlow
                .asMergedLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.rvUser.scrollToPosition(0)
                }
        }

    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            mainAdapter.refresh()
        }
    }




}