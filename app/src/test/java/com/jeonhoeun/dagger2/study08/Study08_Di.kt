package com.jeonhoeun.dagger2.study08

import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import javax.inject.Inject


/**
 * 멀티바인딩을 이용하면 여러 모듈에 있는 동일타입을 하나의 Set,Map으로 관리 가능하다
 * 종류:
 * -Set 멀티바인딩
 * -Map 멀티바인딩
 *
 * Set:
 *   @Provides 에 @IntoSet  @ElementsIntoSet 을 쓸수있다. @IntoSet은 하나의 객체를 추가시 쓰고,
 *   Set<Object>를 한번에 추가하고싶을때는 @ElementsIntoSEt을 쓴다.
 *
 * Map:
 *   @Provides 에 @IntoMap 과 key 를 추가해야된다.
 *   이때 key는 String, class, int, long 이 가능하다
 *
 *   map의 경우 사용자 정의키를 만들고 이용 가능하다.
 *   (사용자 정의키는 이번 스터디에서 생략)
 *
 * 상속된 서브컴포넌트에서도 멀티 바인딩 가능하다 ( 생략 )
 * 추상멀티 바인딩이라는 주제도 있다 ( 생략 )
 */

class IntoSetClass{
    @Inject
    lateinit var strs : Set<String>
}

@Component( modules = [IntoSet1Module::class,IntoSet2Module::class])
interface IntoSetComponent {
    fun inject(obj: IntoSetClass)
}

@Module
class IntoSet1Module{
    @Provides
    @IntoSet
    fun provideStr() = "Str1"
}

@Module
class IntoSet2Module{
    @Provides
    @IntoSet
    fun provideStr() = "Str2"
}


class IntoMapClass{
    @Inject
    lateinit var map1 : Map<String,String>

    @Inject
    lateinit var map2 : Map<Class<*>,String>
}

@Component(modules = [IntoMap1Module::class,IntoMap2Module::class])
interface IntoMapComponent{
    fun inject(obj:IntoMapClass)
}

@Module
class IntoMap1Module{
    @Provides
    @IntoMap
    @StringKey("1")
    fun provideString1() = "STR1"

    @Provides
    @IntoMap
    @ClassKey(IntoMapClass::class)
    fun provideString2() = "STRClass1"
}

@Module
class IntoMap2Module{
    @Provides
    @IntoMap
    @StringKey("2")
    fun provideString1() = "STR2"

    @Provides
    @IntoMap
    @ClassKey(IntoSetClass::class)
    fun provideString2() = "STRClass2"
}