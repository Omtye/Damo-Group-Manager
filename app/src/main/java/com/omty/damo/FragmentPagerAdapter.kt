package com.omty.damo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentPagerAdapter : FragmentPagerAdapter {

    var behavior: Int = 5

    //Commit용 주석테스

    private val fragmentTitleList = mutableListOf("A","B","C","D","E")

    constructor(fm: FragmentManager) : super(fm)


    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 ->  TabFragment1()
            1 ->  TabFragment2()
            2 ->  TabFragment3()
            3 ->  TabFragment4()
            4 ->  IndividualFragment()
            else -> TabFragment1()
        }


    }

    

    override fun getCount(): Int = behavior


}
