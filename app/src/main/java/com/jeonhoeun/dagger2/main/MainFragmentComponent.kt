package com.jeonhoeun.dagger2.main

import com.jeonhoeun.dagger2.di.FragmentScope
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlin.random.Random

@FragmentScope
@Subcomponent( modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    fun inject( fragment:MainFragment)

    @Subcomponent.Builder
    interface Builder{
        @BindsInstance
        fun setFragment(fragment: MainFragment) : Builder
        fun build() : MainFragmentComponent
    }
}

@Module
class MainFragmentModule{
    @Provides
    @FragmentScope
    fun provideInt() = Random.nextInt()
}