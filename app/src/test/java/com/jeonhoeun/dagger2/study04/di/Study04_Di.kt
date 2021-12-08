package com.jeonhoeun.dagger2.study04.di

import dagger.Component
import dagger.Lazy
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider

/**
 * 초기화에 시간이 필요한 경우 Lazy주입이 가능하다.
 * 방법은 바인드된 타입을 Lazy<T> 타입으로 하면된다.
 * 해서 Lazy<T>.get() 호출 전까지는 객체 초기화를 늦출 수 있다.
 *
 */

@Component( modules=[SimpleCounterModule::class])
interface SimpleCounterComponent{
    fun inject(simpleCounter:SimpleCounterHavingLazyInject)
    fun inject(simpleCounter: SimpleCounterHavingProviderInject)
}

@Module
class SimpleCounterModule{
    var next=100

    @Provides
    fun provideInteger():Int {
        println("providing integer")
        return next++
    }
}

class SimpleCounterHavingLazyInject{
    @Inject
    internal lateinit var lazy : Lazy<Integer>

    fun printLazy(){
        println("printing..")
        println(lazy.get())
        println(lazy.get())
        println(lazy.get())
    }
}

/**
 * 초기화에 시간이 필요하면서 매번 새로운 인스턴스를 받고 싶은 경우 provider주입이 가능하다.
 * 방법은 바인드된 타입을 Provider<T> 타입으로 하면된다.
 * Provider<T>.get() 호출 전까지는 객체 초기화를 늦추면서 매번 새로운 객체를 받을 수 있다.
 */

class SimpleCounterHavingProviderInject{
    @Inject
    internal lateinit var provider : Provider<Integer>

    fun printLazy(){
        println("printing..")
        println(provider.get())
        println(provider.get())
        println(provider.get())
    }
}