package ru.androidlab.todoproj

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.androidlab.todoproj.databinding.ActivityMainBinding
import ru.androidlab.todoproj.views.fragments.StateAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                //do nothing here
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //do nothing here
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //do nothing here
            }

        })
    }

    private fun initViewPager(){
        var adapter = StateAdapter(supportFragmentManager,lifecycle)
        binding.viewpager.adapter = adapter

        var names:Array<String> = arrayOf("Tasks","Calendar","Settings")
        var image:Array<Int> = arrayOf(
                R.drawable.tasks_selector,
                R.drawable.calendar_selector,
                R.drawable.settings_selector
        )
        TabLayoutMediator(binding.tabLayout,binding.viewpager){tab, position ->
            tab.text = names[position]
            tab.setIcon(image[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
