package ru.androidlab.todoproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import ru.androidlab.todoproj.databinding.ActivityMainBinding
import ru.androidlab.todoproj.views.fragments.TasksFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) ?: TasksFragment()

        savedInstanceState ?: supportFragmentManager.beginTransaction().apply {

            setReorderingAllowed(true)
            add(R.id.fragmentContainerView, fragment)
            commit()

        }
        setContentView(R.layout.activity_main)

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
}
