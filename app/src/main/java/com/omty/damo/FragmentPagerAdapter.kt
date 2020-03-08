package com.omty.damo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class FragmentPagerAdapter : FragmentPagerAdapter {

    var behavior: Int = 5

    private val fragmentTitleList = mutableListOf("A","B","C","D","E")

    constructor(fm: FragmentManager) : super(fm)


    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 ->  TabFragment1()
            1 ->  TabFragment2()
            4 ->  Individual()
            else ->  TabFragment1()
        }


    }

    

    override fun getCount(): Int = behavior


}
