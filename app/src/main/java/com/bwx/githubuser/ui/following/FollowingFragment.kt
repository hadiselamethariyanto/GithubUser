package com.bwx.githubuser.ui.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.databinding.FragmentFollowingBinding
import com.bwx.githubuser.domain.model.Following
import org.koin.android.viewmodel.ext.android.viewModel

private const val ARG_LOGIN = "login"

class FollowingFragment : Fragment() {
    private var login: String? = null
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowingViewModel by viewModel()
    private lateinit var followingAdapter: FollowingAdapter

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
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        setupAdapter()
        login?.let { getData(it) }
        return binding.root
    }

    private fun setupAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        followingAdapter = FollowingAdapter()

        binding.rvFollowing.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = followingAdapter
        }
    }

    private fun getData(login: String) {
        viewModel.getUserFollowing(login).observe(viewLifecycleOwner, followingObserver)
    }

    private val followingObserver = Observer<Resource<List<Following>>> { data ->
        when (data) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                data.data?.let { followingAdapter.updateData(it) }
            }
            is Resource.Error -> {

            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, param1)
                }
            }
    }


}