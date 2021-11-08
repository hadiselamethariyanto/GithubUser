package com.bwx.githubuser.ui.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bwx.githubuser.databinding.ItemFollowerBinding
import com.bwx.githubuser.model.FollowingResponse

class FollowingAdapter(private val listFollowing: List<FollowingResponse>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    class ViewHolder(private val view: ItemFollowerBinding) : RecyclerView.ViewHolder(view.root) {
        fun bindItem(following: FollowingResponse) {
            view.tvLogin.text = following.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size
}