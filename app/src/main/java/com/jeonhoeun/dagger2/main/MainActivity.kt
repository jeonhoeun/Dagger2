package com.jeonhoeun.dagger2.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jeonhoeun.dagger2.MyApp
import com.jeonhoeun.dagger2.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences : SharedPreferences

    @Inject
    lateinit var activityName : String

    val component by lazy{
        (application as MyApp)
            .component
            .mainActivityComponentBuilder()
            .setActivity(this@MainActivity)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()

        findViewById<Button>(R.id.button_replace_fragment).setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()
        }

    }
}