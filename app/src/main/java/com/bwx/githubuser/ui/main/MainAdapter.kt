package com.bwx.githubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bwx.githubuser.R
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.databinding.ItemUserBinding
import com.bwx.githubuser.domain.model.User

class MainAdapter : PagingDataAdapter<UserEntity, MainAdapter.ViewHolder>(DataDifferntiator) {

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
            val user = User(
                id = item.id,
                avatar_url = item.avatar_url,
                login = item.login,
                repos_url = item.repos_url,
                name = item.name,
                email = item.email,
                created_at = item.created_at
            )
            holder.bindItem(user, onItemClickCallback)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    object DataDifferntiator : DiffUtil.ItemCallback<UserEntity>() {

        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}