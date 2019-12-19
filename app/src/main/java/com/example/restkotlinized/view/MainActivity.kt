package com.example.restkotlinized.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.restkotlinized.R
import com.example.restkotlinized.view.fragments.SectionPagerAdapter
import com.example.restkotlinized.viewmodel.MainViewModel
import com.example.restkotlinized.viewmodel.MainViewModelFactory
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.Disposable
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private lateinit var viewPager: ViewPager
    private lateinit var title: TextView

    private val mainPageNum = 0
    private val detailedPageNum = 1
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)

        viewModel =
            ViewModelProviders.of(this, MainViewModelFactory(this)).get(MainViewModel::class.java)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        title = findViewById(R.id.titleApp)

        title.setOnClickListener {
            viewModel.onTitleClick()
            viewPager.currentItem = mainPageNum
        }

        viewModel.selectedItem.observe(this, Observer {
            viewPager.currentItem = detailedPageNum
        })
    }

    override fun onBackPressed() {
        viewPager.currentItem = mainPageNum
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
