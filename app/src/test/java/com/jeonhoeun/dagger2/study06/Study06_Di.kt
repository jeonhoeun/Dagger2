package com.jeonhoeun.dagger2.study06

import com.jeonhoeun.dagger2.study03.Child
import com.jeonhoeun.dagger2.study03.Parent
import dagger.*
import java.util.*
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * 바인딩 종류
 *
 * @Binds
 *  -추상 메소드에 붙인다
 *  -파라미터가 구현체가 된다.
 *  -반환형으로 구현체를 바인딩할수있다.
 *  -만일 기존에 child타입으로 바인딩한게 있다면 부모타입으로 바인딩이 가능하게해준다.
 *
 *
 * @BindsOptionalOf
 *  -추상 메소드에 붙인다.
 *  -매개변수가 없다
 *  -반환형이 있어야 한다.
 *  -이와같은형우 타겟이 inject를 해야만 값이 있다
 *
 *  ==> 다만 딱히 사용할 이유는 없어보인다.
 *
 * @BindsInstance
 *  -컴포넌트 빌더의 세터 메소드에 붙인다
 *  -인스턴스를 넘겨줌으로 해당 인스턴스를 주입받을 수있다.
 */


/**
 * @Binds 예제
 * -핵심은 사실 인터페이스를 리턴하면서 구현체를 넣는것이다.
 * -차일드와 부모를 모두 차일드를 이용해서 제공 가능하다
 */

@Singleton
@Component( modules=[BindsModule::class])
interface BindsComponent{
    fun getParent() : Parent
    fun getChild() : Child
    fun getItf() : Itf
}

@Module
abstract class BindsModule{
    companion object{
        @Singleton
        @Provides
        fun provideChild() = Child()
    }
    @Binds abstract fun parent(impl:Child) : Parent

    @Binds abstract fun itf(impl:ItfImpl) : Itf // <- 이게 핵심
}

class Parent{
    val name="parent"
}

class Child{
    val myName="child"
    val name="child"
}

interface Itf{
    fun print()
}

class ItfImpl @Inject constructor() : Itf{
    override fun print() {
        println("itfimpl")
    }
}

@Component( modules=[BindsOptionalOfModule::class, StrModule::class])
interface BindsOptionalOfComponent{
    fun inject(cls:OptionalCls)
    fun getStr() : String
}

@Module
abstract class BindsOptionalOfModule{

    @BindsOptionalOf
    abstract fun optStr() : String
}

@Module
class StrModule{
    @Provides
    fun str() = "STR"
}

class OptionalCls{
    @Inject
    lateinit var str : Optional<String>
    @Inject
    lateinit var strP : Optional<Provider<String>>
    @Inject
    lateinit var strL : Optional<Lazy<String>>
}


/**
 * BindsInstance를 이용하면 모듈들에서 필요로하는 인스턴스를 제공할 수 있다.
 * 아래 예제에서는 Obj2는 Obj를 필요로 하는데 이를 프로비젼메소드( setObj )를 통해서
 * 공급하고 있다.
 */

@Component( modules=[BindsInstModule::class])
interface BindsInstComponent{
    fun getObjs() : Obj2
    @Component.Builder
    interface Builder{
        @BindsInstance fun setObj(obj:Obj) : Builder
        fun build() : BindsInstComponent
    }
}

@Module
class BindsInstModule{
    @Provides
    fun provideObj2(obj: Obj) : Obj2{
        return Obj2(obj)
    }
}

class Obj{
    var name="Obj"
}

class Obj2 constructor(val obj:Obj){
    fun name() = obj.name
}