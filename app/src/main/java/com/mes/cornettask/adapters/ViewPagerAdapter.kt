package com.mes.cornettask.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mes.cornettask.ui.discoverScreen.DiscoverFragment
import com.mes.cornettask.ui.searchScreen.SearchFragment

class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var discoverFragment: DiscoverFragment = DiscoverFragment()
    var searchFragment: SearchFragment = SearchFragment()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                discoverFragment
            }
            1 -> {
                searchFragment
            }

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return 2
    }
}