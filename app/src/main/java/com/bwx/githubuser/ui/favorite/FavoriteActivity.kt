package com.bwx.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.R
import com.bwx.githubuser.data.local.entity.UserEntity
import com.bwx.githubuser.databinding.ActivityFavoriteBinding
import com.bwx.githubuser.ui.setting.SettingActivity
import com.bwx.githubuser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this@FavoriteActivity.application)
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        viewModel.getListFavoriteUser().observe(this, {
            setupAdapter(it)
        })

        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.favorite)
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun setupAdapter(listUser: List<UserEntity>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = FavoriteAdapter(listUser)
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                viewModel.deleteFavoriteUser(data)
            }
        })

        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.adapter = adapter
    }

}