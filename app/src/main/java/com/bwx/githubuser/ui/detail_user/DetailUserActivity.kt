package com.bwx.githubuser.ui.detail_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bwx.githubuser.R
import com.bwx.githubuser.data.local.entity.UserEntity
import com.bwx.githubuser.databinding.ActivityDetailUserBinding
import com.bwx.githubuser.databinding.ContentDetailUserBinding
import com.bwx.githubuser.model.DetailUserResponse
import com.bwx.githubuser.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserBinding: ActivityDetailUserBinding
    private lateinit var detailContentDetailUserBinding: ContentDetailUserBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserBinding.root)
        detailContentDetailUserBinding = detailUserBinding.detailContent

        val user = intent.getParcelableExtra<UserEntity>(EXTRA_USER) as UserEntity
        val factory = ViewModelFactory.getInstance(this@DetailUserActivity.application)

        viewModel =
            ViewModelProvider(
                this,
                factory
            ).get(DetailViewModel::class.java)

        viewModel.getDetailUser(user.login.toString()).observe(this, {
            setUser(it)
        })

        viewModel.isLoading.observe(this, {
            setLoading(it)
        })

        detailUserBinding.fabAdd.setOnClickListener {
            viewModel.setFavoriteUser()
        }

        user.login?.let { setupTab(it) }

    }

    private fun setupTab(login: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        detailContentDetailUserBinding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            detailContentDetailUserBinding.tabs,
            detailContentDetailUserBinding.viewPager
        ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUser(user: DetailUserResponse) {
        with(detailContentDetailUserBinding) {
            tvName.text = user.name
            tvUsername.text = String.format(resources.getString(R.string.username), user.login)
            tvDescription.text = String.format(
                resources.getString(R.string.description),
                user.name,
                user.location,
                user.company
            )
            Glide.with(this@DetailUserActivity).load(user.avatarUrl).circleCrop().into(imgUser)
        }

    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            detailUserBinding.refresh.visibility = View.VISIBLE
            detailUserBinding.detailContent.layout.visibility = View.INVISIBLE
        } else {
            detailUserBinding.refresh.visibility = View.GONE
            detailUserBinding.detailContent.layout.visibility = View.VISIBLE
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_follower
        )
        const val EXTRA_USER = "extra_user"
    }

}