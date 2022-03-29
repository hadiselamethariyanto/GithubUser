package com.bwx.githubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.databinding.ActivityMainBinding
import com.bwx.githubuser.domain.model.User
import com.bwx.githubuser.paging.asMergedLoadStates
import kotlinx.coroutines.flow.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        initSwipeToRefresh()
    }


    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainAdapter = MainAdapter()
        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                mainViewModel.getDetailUser(data.login)
                    .observe(this@MainActivity, detailUserObserver)
            }
        })

        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
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


        mainViewModel.getUsers().observe(this@MainActivity) {
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


    private val detailUserObserver = Observer<Resource<User>> { data ->
        if (data != null) {
            when (data) {
                is Resource.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is Resource.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(
                        this,
                        "name:${data.data?.name} \n email:${data.data?.email} \n created_at:${data.data?.created_at}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}