package com.jeonhoeun.dagger2.main

import com.jeonhoeun.dagger2.di.ActivityScope
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent( modules = [MainActivityModule::class])
@ActivityScope
interface MainActivityComponent {
    fun inject(activity:MainActivity)
    fun getMainFragmentComponentBuilder() : MainFragmentComponent.Builder
    @Subcomponent.Builder
    interface Builder{
        @BindsInstance
        fun setActivity(activity: MainActivity) : Builder
        fun build() : MainActivityComponent
    }
}

@Module
class MainActivityModule{
    @Provides
    @ActivityScope
    fun provideActivityName():String = MainActivity::class.simpleName!!
}