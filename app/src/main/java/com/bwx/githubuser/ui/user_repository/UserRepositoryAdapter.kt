package com.bwx.githubuser.ui.user_repository

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bwx.githubuser.databinding.ItemRepositoryBinding
import com.bwx.githubuser.domain.model.Repository
import java.util.*
import kotlin.collections.ArrayList

class UserRepositoryAdapter : RecyclerView.Adapter<UserRepositoryAdapter.ViewHolder>() {
    private val list = ArrayList<Repository>()

    fun updateData(new: List<Repository>) {
        list.clear()
        list.addAll(new)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(repository: Repository) {
            with(binding) {
                tvName.text = repository.name
                tvDescription.text = repository.description
                tvLanguage.text = repository.language
                DrawableCompat.setTint(binding.circle.drawable, getRandomColor())

                if (repository.description == "null") {
                    tvDescription.visibility = View.GONE
                } else {
                    tvDescription.visibility = View.VISIBLE
                }

                if (repository.language == "null") {
                    tvLanguage.visibility = View.GONE
                    circle.visibility = View.GONE
                } else {
                    tvLanguage.visibility = View.VISIBLE
                    circle.visibility = View.VISIBLE
                }
            }
        }

        private fun getRandomColor(): Int {
            val random = Random()
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size
}