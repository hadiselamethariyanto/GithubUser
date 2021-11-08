package com.bwx.githubuser.ui.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bwx.githubuser.databinding.ItemFollowerBinding
import com.bwx.githubuser.model.FollowerResponse

class FollowerAdapter(private val listFollower: List<FollowerResponse>) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemFollowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(follower: FollowerResponse) {
            binding.tvLogin.text = follower.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFollower[position])
    }

    override fun getItemCount(): Int = listFollower.size
}