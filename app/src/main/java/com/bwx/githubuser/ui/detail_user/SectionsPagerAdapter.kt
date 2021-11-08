package com.bwx.githubuser.ui.detail_user

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bwx.githubuser.ui.follower.FollowerFragment
import com.bwx.githubuser.ui.following.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.newInstance(login)
            1 -> fragment = FollowerFragment.newInstance(login)
        }
        return fragment as Fragment
    }

}