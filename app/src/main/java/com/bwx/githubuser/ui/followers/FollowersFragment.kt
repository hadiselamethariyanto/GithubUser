package com.bwx.githubuser.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.databinding.FragmentFollowersBinding
import com.bwx.githubuser.domain.model.Followers
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_LOGIN = "login"

class FollowersFragment : Fragment() {
    private var login: String? = null
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersAdapter: FollowersAdapter
    private val followersViewModel: FollowersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(ARG_LOGIN)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        setupAdapter()
        login?.let { getData(it) }
        return binding.root
    }

    private fun setupAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        followersAdapter = FollowersAdapter()
        binding.rvFollowers.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = followersAdapter
        }
    }

    private fun getData(login: String) {
        followersViewModel.getUserFollowers(login).observe(viewLifecycleOwner, followersObserver)
    }

    private val followersObserver = Observer<Resource<List<Followers>>> { data ->
        when (data) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                data.data?.let { followersAdapter.setData(it) }
            }
            is Resource.Error -> {

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, param1)
                }
            }
    }


}