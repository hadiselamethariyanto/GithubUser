package com.bwx.githubuser.ui.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.databinding.FragmentFollowerBinding
import com.bwx.githubuser.model.FollowerResponse
import com.bwx.githubuser.ui.main.MainViewModel

private const val ARG_LOGIN = "login"

class FollowerFragment : Fragment() {

    private var login: String? = null
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

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
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            mainViewModel =
                ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                    MainViewModel::class.java
                )

            login?.let {
                mainViewModel.getFollower(it).observe(requireActivity(), { list ->
                    setupListFollower(list)
                })
            }

            mainViewModel.isLoadingFragment.observe(requireActivity(), {
                setLoading(it)
            })
        }

    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.rvFollower.visibility = View.GONE
            binding.refresh.visibility = View.VISIBLE
        } else {
            binding.rvFollower.visibility = View.VISIBLE
            binding.refresh.visibility = View.GONE
        }
    }

    private fun setupListFollower(list: List<FollowerResponse>) {
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val adapter = FollowerAdapter(list)
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)

        binding.rvFollower.setHasFixedSize(true)
        binding.rvFollower.layoutManager = linearLayoutManager
        binding.rvFollower.adapter = adapter
        binding.rvFollower.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, param1)
                }
            }
    }

}