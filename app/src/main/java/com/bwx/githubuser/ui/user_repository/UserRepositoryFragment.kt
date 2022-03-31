package com.bwx.githubuser.ui.user_repository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.R
import androidx.lifecycle.Observer
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.databinding.FragmentUserRepositoryBinding
import com.bwx.githubuser.domain.model.Repository
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

private const val ARG_LOGIN = "login"

class UserRepositoryFragment : Fragment() {
    private var login: String? = null
    private var _binding: FragmentUserRepositoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserRepositoryViewModel by viewModel()
    private lateinit var repositoryAdapter: UserRepositoryAdapter

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
        // Inflate the layout for this fragment
        _binding = FragmentUserRepositoryBinding.inflate(layoutInflater, container, false)
        setupAdapter()
        getRepositories()
        return binding.root
    }

    private fun getRepositories() {
        login?.let {
            viewModel.getUserRepository(it).observe(viewLifecycleOwner, repositoriesObserver)
        }
    }

    private fun setupAdapter() {
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        repositoryAdapter = UserRepositoryAdapter()
        binding.rvRepositories.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = repositoryAdapter
        }
    }

    private val repositoriesObserver = Observer<Resource<List<Repository>>> { data ->
        when (data) {
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                data.data?.let { repositoryAdapter.updateData(it) }
            }
            is Resource.Error -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            UserRepositoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOGIN, param1)
                }
            }
    }

}