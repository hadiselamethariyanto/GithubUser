package com.bwx.githubuser.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bwx.githubuser.data.local.entity.UserEntity
import com.bwx.githubuser.databinding.ItemUserBinding
import com.bwx.githubuser.model.UserRespons
import com.bwx.githubuser.ui.detail_user.DetailUserActivity

class MainAdapter(private val listUser: List<UserRespons>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(user: UserRespons) {
            with(binding) {
                tvName.text = user.login
                tvLinkRepository.text = user.reposUrl
                Glide.with(itemView.context).load(user.avatarUrl).circleCrop().into(imgUser)

            }

            itemView.setOnClickListener {
                val userEntity = UserEntity(
                    id = user.id,
                    login = user.login,
                    repos_url = user.reposUrl,
                    avatar_url = user.avatarUrl
                )

                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, userEntity)
                itemView.context.startActivity(intent)
            }

        }
    }

}