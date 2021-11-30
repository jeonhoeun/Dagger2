package com.jeonhoeun.dagger2.study01.di

import dagger.Component
import dagger.Module
import dagger.Provides


/**
 * component 는 오브젝트 그래프의 진입점이며 (일종의 인터페이스 )
 * component 로 부터 얻을 수 있는 재료들은 module을 통해서 얻는다.( provisioning 도 가능 )
 */
@Component(modules = [SimpleModule::class])
interface SimpleComponent{
    fun getString() : String // SimpleModule의 provideString을 통해서 재료를 받음
}


/**
 * module은 component로 재료를 공급하며 이때 공급하는 메소드에는 @Provides 를 붙인다.
 */
@Module
class SimpleModule{
    @Provides
    fun provideString() = "SimpleString"
}