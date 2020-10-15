package com.mes.cornettask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.mes.cornettask.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        createTabs()
        setTabClickListener()
        setViewPagerAdapter()
    }

    /**
     * set adapter for view pager & link view pagers with tabs
     * */
    private fun setViewPagerAdapter() {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        val viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        viewPager.currentItem = 0  // set default selected item
    }

    /**
     * set tab listener for each item to viewPager screen
     * */
    private fun setTabClickListener() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    /**
     * this function is used to create/add tabs to TabLayout
     * */
    private fun createTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Discover"))
        tabLayout.addTab(tabLayout.newTab().setText("Search"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }
}