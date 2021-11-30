package com.jeonhoeun.dagger2.study02.di

import androidx.annotation.Nullable
import dagger.Component
import dagger.Module
import dagger.Provides


/**
 * 아래와같이 component에 동일한 타입이 제공되면 컴파일시
 * [Dagger/DuplicateBindings] java.lang.String is bound multiple times:
 * 이와같이 빌드 오류가 난다.
 */

@Component(modules = [ProvideStringTwiceErrorExampleModule::class])
interface ProvideSameTypeTwiceErrorComponent{
    fun getString() : String
}

@Module
class ProvideStringTwiceErrorExampleModule{
    @Provides
    fun provideStringOne() = "StringOne"

//    아래 코드를 주석제거하면 Dagger 빌드 오류 발
//    @Provides
//    fun provideStringTwo() = "StringTwo"
}


/**
 * 모듈을 통하여 인스턴스를 공급받는 예제로, 인스턴스의파라미터는 모듈 자체에서 공급중인 예제임.
 */
@Component( modules = [ProvideInstanceModule::class])
interface ProvideInstanceByModuleComponent{
    fun getInstance() : Instance
}

@Module
class ProvideInstanceModule{
    @Provides
    fun provideNum() = 100
    @Provides
    fun provideName() = "Name"
    @Provides
    fun provideInstance(num:Int,name:String) = Instance(num,name)
}

class Instance(val num:Int, val name:String)


/**
 * Nullable을 제공하기 위해서는 모듈과 컴포넌트 양쪽에 @Nullable을 붙여준다.
 * 코틀린의 경우 @Nullable대신 ? (옵셔널) 을 이용해도 된다.
 */
@Component(modules = [NullableModule::class])
interface NullableComponent{
    @Nullable
    fun getNullableInteger() : Integer
}

@Module
class NullableModule{
    @Provides
    @Nullable
    fun provideNullableString() :Integer? = null
}

/**
 * 모둘은 상속이 가능하다
 * 이때 Child에서 부모와 같은 타입을 provide하는경우
 * [Dagger/DuplicateBindings] com.jeonhoeun.dagger2.study02.di.Instance is bound multiple times:
 * 라는 Dagger빌드 오류가 발생한다.
 */

@Component(modules = [ChildModule::class])
interface InheritanceComponent{
    fun getInstance() : Instance
}

@Module
class ParentModule{
    @Provides
    fun provideInstance() = Instance(100,"Parent")
}

@Module( includes = [ParentModule::class])
class ChildModule{
//    이부분을 주석제거하면 빌드오류가 발생한다.
//    @Provides
//    fun provideInstance() = Instance(200,"Child")
}