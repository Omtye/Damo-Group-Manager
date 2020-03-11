package com.omty.damo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View.OVER_SCROLL_NEVER
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.Toolbar
import androidx.core.internal.view.SupportMenuItem
import kotlinx.android.synthetic.main.activity_main.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = "Damo"

        view_Pager.adapter = FragmentPagerAdapter(supportFragmentManager)

        var params : AppBarLayout.LayoutParams = toolbar.layoutParams as AppBarLayout.LayoutParams


        view_Pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position){
                    0 -> {
                        actionbarAnimate(0F)
                    //    params.setScrollFlags(0)
                    //    params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                    //    toolbar.layoutParams = params

                    }

                    else -> {
                        actionbarAnimate(toolbar.height.toFloat() * -1)
                    //    params.setScrollFlags(0)
                    //    params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
                    //    toolbar.layoutParams = params

                    }

                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // 페이지가 스크로 상태
            }
        })





        //ViewPager와 Toolbar를 연결
        tabs.setupWithViewPager(view_Pager)

        //Tabitem에 이미지를 붙임
        tabs.getTabAt(0)?.setIcon(R.drawable.icons8_home_96)
        tabs.getTabAt(1)?.setIcon(R.drawable.icons8_user_groups_64)
        tabs.getTabAt(2)?.setIcon(R.drawable.icons8_search_480)
        tabs.getTabAt(3)?.setIcon(R.drawable.icons8_bell_96)
        tabs.getTabAt(4)?.setIcon(R.drawable.icons8_person_256)


    }

    //위에서 생성한 툴바를 메뉴에 붙여줌
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_bar, menu)

        return true
    }

    private fun actionbarAnimate(Value : Float){
        appbar.animate()
            .translationY(Value.toFloat())
            .alpha(10F).setDuration(130)
            .setInterpolator(DecelerateInterpolator())
    }






}
