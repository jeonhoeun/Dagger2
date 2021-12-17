package com.jeonhoeun.dagger2

import android.app.Application
import com.jeonhoeun.dagger2.di.DaggerAppComponent

class MyApp : Application(){
    val component by lazy{
        DaggerAppComponent.factory().create(this@MyApp)
    }
}