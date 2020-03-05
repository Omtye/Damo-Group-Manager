package com.omty.damo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity() {

    private var viewPager : ViewPager? = null
    private var pagerAdapter : ViewPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbars : Toolbar

        toolbars = toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Damo"

        viewPager = view_Pager
        pagerAdapter = ViewPagerAdapter(this)


        viewPager!!.adapter = pagerAdapter





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
