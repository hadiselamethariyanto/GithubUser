package com.bwx.githubuser.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bwx.githubuser.data.local.entity.UserEntity
import com.bwx.githubuser.databinding.ItemFavoriteUserBinding
import com.bwx.githubuser.ui.detail_user.DetailUserActivity

class FavoriteAdapter(private val listFavorite: List<UserEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(private val view: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bindItem(user: UserEntity, onItemClickCallback: OnItemClickCallback) {
            view.tvName.text = user.name
            view.tvLocation.text = user.location
            view.favorite.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USER, user)
                itemView.context.startActivity(intent)
            }

            Glide.with(itemView.context).load(user.avatar_url).circleCrop().into(view.imgUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listFavorite[position], onItemClickCallback)
    }

    override fun getItemCount(): Int = listFavorite.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }
}