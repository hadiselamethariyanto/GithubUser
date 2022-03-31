package com.bwx.githubuser.ui.detail_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bwx.githubuser.R
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.databinding.ContentDetailUserBinding
import com.bwx.githubuser.databinding.FragmentDetailUserBinding
import com.bwx.githubuser.domain.model.User
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel

class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentDetailBinding: ContentDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        contentDetailBinding = binding.detailContent
        val login = arguments?.getString(USER_KEY) ?: ""
        setupTabs(login)
        setUserProfile(login)
        return binding.root
    }

    private fun setupTabs(login: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity(), login)
        contentDetailBinding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            contentDetailBinding.tabs,
            contentDetailBinding.viewPager
        ) { tab, position ->
            tab.icon = ContextCompat.getDrawable(requireActivity(), TAB_ICONS[position])
        }.attach()
    }

    private fun setUserProfile(login: String) {
        viewModel.getDetailUser(login).observe(viewLifecycleOwner, detailUserObserver)
    }

    private val detailUserObserver = Observer<Resource<User>> { data ->
        if (data != null) {
            when (data) {
                is Resource.Loading -> {
                    binding.loading.viewLoading.visibility = View.VISIBLE
                    binding.fabAdd.visibility = View.GONE
                }
                is Resource.Success -> {
                    val user = data.data
                    with(contentDetailBinding) {
                        tvName.text = user?.name
                        tvLogin.text = resources.getString(R.string.user_id, user?.login)
                        tvCompany.text = user?.company
                        tvLocation.text = user?.location
                        tvRepository.text = user?.public_repos.toString()
                        tvFollowing.text = user?.following.toString()
                        tvFollowers.text = user?.followers.toString()

                        setVisibilityView(tvLocation, user?.location.toString())
                        setVisibilityView(tvCompany, user?.company.toString())

                    }

                    binding.loading.viewLoading.visibility = View.GONE
                    binding.fabAdd.visibility = View.VISIBLE

                    Glide.with(requireActivity()).load(user?.avatar_url)
                        .placeholder(R.drawable.ic_user)
                        .circleCrop().into(contentDetailBinding.imgUser)
                }
                is Resource.Error -> {
                    binding.loading.viewLoading.visibility = View.GONE
                    binding.error.viewError.visibility = View.VISIBLE
                    binding.error.tvError.text = data.message
                }
            }
        }
    }

    private fun setVisibilityView(view: View, data: String) {
        if (data == "null") {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    companion object {

        private val TAB_ICONS =
            intArrayOf(R.drawable.ic_repository, R.drawable.ic_following, R.drawable.ic_followers)

        const val USER_KEY = "user_key"
    }

}