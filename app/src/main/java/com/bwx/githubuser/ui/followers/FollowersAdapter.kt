package com.bwx.githubuser.ui.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bwx.githubuser.R
import com.bwx.githubuser.databinding.ItemUserBinding
import com.bwx.githubuser.domain.model.Followers

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    private val list = ArrayList<Followers>()

    fun setData(new: List<Followers>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: Followers) {
            with(binding) {
                tvName.text = data.login
                tvLinkRepository.text = data.repos_url

                Glide.with(itemView.context).load(data.avatar_url).placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user).circleCrop().into(imgUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size
}