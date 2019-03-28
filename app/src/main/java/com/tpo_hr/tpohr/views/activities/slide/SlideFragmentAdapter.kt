package com.tpo_hr.tpohr.views.activities.slide

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SlideFragmentAdapter(fm: FragmentManager?, private val listFragments: ArrayList<Fragment>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = listFragments[position]

    override fun getCount(): Int = listFragments.size
}