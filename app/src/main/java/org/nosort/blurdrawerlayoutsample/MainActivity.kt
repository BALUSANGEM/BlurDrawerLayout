package org.nosort.blurdrawerlayoutsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        startGravityActivity.setOnClickListener { gotoActivity(LeftDrawerActivity::class.java) }
        endGravityActivity.setOnClickListener { gotoActivity(RightDrawerActivity::class.java) }
    }

    private fun gotoActivity(className: Class<*>) {
        startActivity(Intent(this, className))
    }
}
