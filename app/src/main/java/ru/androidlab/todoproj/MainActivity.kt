package ru.androidlab.todoproj

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.androidlab.todoproj.databinding.ActivityMainBinding
import ru.androidlab.todoproj.views.fragments.StateAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val RC_SIGN_IN = 120
    }


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

    private fun initViewPager() {
        val adapter = StateAdapter(supportFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter

        val names: Array<String> = arrayOf("Tasks", "Calendar", "Profile")
        val image: Array<Int> = arrayOf(
            R.drawable.tasks_selector,
            R.drawable.calendar_selector,
            R.drawable.ic_baseline_person_24
        )
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
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
