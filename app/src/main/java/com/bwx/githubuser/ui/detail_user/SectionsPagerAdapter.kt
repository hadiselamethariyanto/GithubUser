package com.bwx.githubuser.ui.detail_user

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bwx.githubuser.ui.followers.FollowersFragment
import com.bwx.githubuser.ui.following.FollowingFragment
import com.bwx.githubuser.ui.user_repository.UserRepositoryFragment

class SectionsPagerAdapter(activity: FragmentActivity, val login: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = UserRepositoryFragment.newInstance(login)
            1 -> fragment = FollowingFragment.newInstance(login)
            2 -> fragment = FollowersFragment()
        }
        return fragment as Fragment
    }

}