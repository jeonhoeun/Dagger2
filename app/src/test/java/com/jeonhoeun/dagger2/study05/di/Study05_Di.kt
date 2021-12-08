package com.jeonhoeun.dagger2.study05.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy
import javax.inject.*

/**
 * 한정자(Qualifier)를 이용하면 동일한 클래스타입을 구분해서 제공, 주입 할 수 있다.
 */
@Component( modules = [UsingNamedModule::class])
interface UsingNamedComponent{
    fun inject(usingNamedClass: UsingNamedClass)
}

/**
 * 아래 annotation중에 Named를 빼면 String을 두번 provide하기때문에 duplicated 빌드 오류가 발생한다
 */
@Module
class UsingNamedModule{
    @Provides
    @Named("name1")
    fun provideName1() = "Name1"

    @Provides
    @Named("name2")
    fun provideName2() = "Name2"
}

class UsingNamedClass{
    @Inject
    @Named("name1")
    lateinit var name1 : String

    @Inject
    @Named("name2")
    lateinit var name2 : String

    fun printNames(){
        println("$name1 $name2")
    }
}

/**
 * 사용자정의 한정자(user defined qualifier)는 사용자가 named가 아닌 특정 tag를 정의하여
 * name와 동일하게 사용할 수 있다.
 */

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Name11

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Name22

@Component( modules = [UsingUserDefinedQualifierModule::class])
interface UsingUserDefinedQualifierComponent{
    fun inject(usingUserDefinedQualifierClass: UsingUserDefinedQualifierClass)
}

@Module
class UsingUserDefinedQualifierModule{
    @Provides
    @Name11
    fun provideName1() = "user defined 1"

    @Provides
    @Name22
    fun provideName2() = "user defined 2"
}

class UsingUserDefinedQualifierClass{
    @Inject
    @field:Name11
    lateinit var name1 : String

    @Inject
    @field:Name22
    lateinit var name2 : String

    fun printNames(){
        println("$name1 $name2")
    }
}

/**
 * 범위(Scope)지정 - Singleton
 * 1. @Singleton 을 이용하면 컴포넌트 스코프 내에서는 하나의 객체로 통용된다.다 @Provide중에 @Singleton이 있다면
 *    component에도 @Singleton이 필요하다.
 * 2. @Reuseable 스코프 제약이 없기때문에 컴포넌트에 @Reuseable이 필요없다. 이전 객체 재사용이 가능하다면 재사용하고
 *    그렇지 않다면 새로 생성한다. 즉 동일성을 보장하지 않으며 메모리 관리를 위하여 사용된다.
 *
 * 3. 사용자정의로 스코프를 직접 만들어서 컴포넌트 범위를 지정할 수 있다.
 */

@Singleton
@Component( modules=[SingletonModule::class])
interface SingletonComponent{
    fun getObject() : Object
}

@Module
class SingletonModule{
    @Provides
    @Singleton
    fun provideObject() = Object()
}

@Component( modules=[ReusableModule::class])
interface ReusableComponent{
    fun getObject() : Object
}

@Module
class ReusableModule{
    @Provides
    @Reusable
    fun provideObject() = Object()
}


@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class UserScope

@UserScope
@Component(modules = [UserDefinedScopeModule::class])
interface UserDefinedScopeComponent{
    fun getObject() : Object
}

@Module
class UserDefinedScopeModule{
    @Provides
    @UserScope
    fun provideObject() = Object()
}