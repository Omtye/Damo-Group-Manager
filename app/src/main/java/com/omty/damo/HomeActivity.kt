package com.omty.damo

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.TableLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.home.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        var toolbars : androidx.appcompat.widget.Toolbar

        toolbars = toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Damo"


     //   getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);

    }

    //위에서 생성한 툴바를 메뉴에 붙여줌
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_bar, menu)

        return true
    }


}