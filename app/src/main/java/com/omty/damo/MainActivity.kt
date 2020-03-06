package com.omty.damo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.viewpager.widget.ViewPager










class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Damo"





        /*viewPager = view_Pager
        pagerAdapter = ViewPagerAdapter(this)


        viewPager!!.adapter = pagerAdapter*/
        Log.d("testt","tabcount : "+tabs.tabCount)
        view_Pager.adapter = FragmentPagerAdapter(supportFragmentManager)

        view_Pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 페이지가 스크롤됐을 때
            }

            override fun onPageSelected(position: Int) {
                // 페이지가 선택됐을 때
            }

            override fun onPageScrollStateChanged(state: Int) {
                // 페이지가 스크로 상태
            }
        })




        tabs.setupWithViewPager(view_Pager)


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



    inner class ViewPagerAdapter : PagerAdapter {

        // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
        private var mContext: Context? = null



        // Context를 전달받아 mContext에 저장하는 생성자 추가.
        constructor(context: Context) {
            mContext = context
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var view: View? = null


            if (mContext != null) {
                // LayoutInflater를 통해 "/res/layout/home.xml"을 뷰로 생성.
                val inflater : LayoutInflater = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                view = inflater.inflate(R.layout.home, container, false)

                val textView : TextView = view.findViewById(R.id.title)
                textView.text = "TEXT $position"

            }

            // 뷰페이저에 추가.
            container.addView(view)

            return view!!
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // 뷰페이저에서 삭제.
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            // 전체 페이지 수는 10개로 고정.
            return 10
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }
    }

}
