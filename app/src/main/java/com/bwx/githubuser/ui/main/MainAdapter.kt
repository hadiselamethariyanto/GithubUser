package com.bwx.githubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bwx.githubuser.R
import com.bwx.githubuser.databinding.ItemUserBinding
import com.bwx.githubuser.domain.model.User

class MainAdapter : PagingDataAdapter<User, MainAdapter.ViewHolder>(DataDifferntiator) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(user: User, onItemClickCallback: OnItemClickCallback) {
            with(binding) {
                tvName.text = user.login
                tvLinkRepository.text = user.repos_url
                Glide.with(itemView.context).load(user.avatar_url).placeholder(R.drawable.ic_user)
                    .circleCrop().into(imgUser)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindItem(item, onItemClickCallback)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    object DataDifferntiator : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}