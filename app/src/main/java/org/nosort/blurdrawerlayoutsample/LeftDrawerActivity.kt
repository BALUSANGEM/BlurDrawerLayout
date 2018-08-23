package org.nosort.blurdrawerlayoutsample

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_left_drawer.*
import org.avantari.blurdrawerlayout.dummy.DummyContent

class LeftDrawerActivity : AppCompatActivity(), RecyclerViewFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_drawer)
        setUpToolbar()
        setUpClickListener()
        blurDrawerLayout.setScrimColor(getColorCompat(android.R.color.transparent))
        createFragmentAt(0)
    }

    private fun createFragmentAt(at: Int) {
        val fragment = when (at) {
            0 -> ScrollFragment()
            1 -> RecyclerViewFragment()
            2 -> SimpleFragment()
            else -> null
        }
        fragment?.let {
            blurDrawerLayout.closeDrawers()
            Handler().postDelayed({
                showFragment(fragment)
            }, 200)
        }
    }

    private fun showFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.contentLayout, fragment)
        ft.commitAllowingStateLoss()
    }

    private fun setUpClickListener() {
        menuone.setOnClickListener {
            createFragmentAt(0)
        }
        menuTwo.setOnClickListener {
            createFragmentAt(1)
        }
        menuThree.setOnClickListener {
            createFragmentAt(2)
        }

    }

    lateinit var toggle: ActionBarDrawerToggle

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(this, blurDrawerLayout, R.string.drawer_open, R.string.drawer_close)
        toggle.isDrawerIndicatorEnabled = true
        blurDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item)
    }

    private fun getColorCompat(color: Int): Int =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                resources.getColor(color, null)
            } else {
                resources.getColor(color)
            }
}
