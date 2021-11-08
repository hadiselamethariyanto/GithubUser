package com.bwx.githubuser.ui.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.databinding.FragmentFollowingBinding
import com.bwx.githubuser.model.FollowingResponse
import com.bwx.githubuser.ui.main.MainViewModel

private const val ARG_LOGIN = "login"

class FollowingFragment : Fragment() {

    private var login: String? = null
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            viewModel =
                ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(
                    MainViewModel::class.java
                )

            viewModel.isLoadingFragment.observe(requireActivity(), {
                setLoading(it)
            })

            login?.let {
                viewModel.getFollowing(it).observe(requireActivity(), { listFollowing ->
                    setupListFollowing(listFollowing)
                })
            }

        }
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.rvFollowing.visibility = View.GONE
            binding.refresh.visibility = View.VISIBLE
        } else {
            binding.rvFollowing.visibility = View.VISIBLE
            binding.refresh.visibility = View.GONE
        }
    }

    private fun setupListFollowing(list: List<FollowingResponse>) {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        val adapter = FollowingAdapter(list)
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)


        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.layoutManager = layoutManager
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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