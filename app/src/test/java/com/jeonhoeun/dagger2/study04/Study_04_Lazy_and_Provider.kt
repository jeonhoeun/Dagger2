package com.jeonhoeun.dagger2.study04

import com.jeonhoeun.dagger2.study04.di.DaggerSimpleCounterComponent
import com.jeonhoeun.dagger2.study04.di.SimpleCounterHavingLazyInject
import com.jeonhoeun.dagger2.study04.di.SimpleCounterHavingProviderInject
import org.junit.Test

class Study_04_Lazy_and_Provider {

    // lazy test
    @Test
    fun lazyTest(){
        val component = DaggerSimpleCounterComponent.create()
        val simpleCounterHavingLazyInject = SimpleCounterHavingLazyInject()
        component.inject(simpleCounterHavingLazyInject)
        simpleCounterHavingLazyInject.printLazy()
    }

    // provider test
    @Test
    fun providerTest(){
        val component = DaggerSimpleCounterComponent.create()
        val simpleCounterHavingProviderInject = SimpleCounterHavingProviderInject()
        component.inject(simpleCounterHavingProviderInject)
        simpleCounterHavingProviderInject.printLazy()
    }
}