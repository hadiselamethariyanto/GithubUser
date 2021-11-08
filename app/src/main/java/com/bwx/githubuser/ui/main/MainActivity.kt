package com.bwx.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwx.githubuser.R
import com.bwx.githubuser.databinding.ActivityMainBinding
import com.bwx.githubuser.model.UserRespons
import com.bwx.githubuser.ui.favorite.FavoriteActivity
import com.bwx.githubuser.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.listUsers.observe(this, { listUser ->
            setupListUser(listUser)
        })

        mainViewModel.isLoading.observe(this, {
            setLoading(it)
        })

    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            mainBinding.rvUser.visibility = View.GONE
            mainBinding.refresh.visibility = View.VISIBLE
        } else {
            mainBinding.rvUser.visibility = View.VISIBLE
            mainBinding.refresh.visibility = View.GONE
        }
    }

    private fun setupListUser(listUser: List<UserRespons>) {

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = MainAdapter(listUser)

        mainBinding.rvUser.setHasFixedSize(true)
        mainBinding.rvUser.layoutManager = linearLayoutManager
        mainBinding.rvUser.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(query).observe(this@MainActivity, { response ->
                    if (response.totalCount > 0) {
                        setupListUser(response.items)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}