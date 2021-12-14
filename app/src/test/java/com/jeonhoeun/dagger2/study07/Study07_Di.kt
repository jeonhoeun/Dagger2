package com.jeonhoeun.dagger2.study07

import androidx.transition.Transition
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

/**
 * Component 를 확장 하는 방법은 두가지가 있다.
 * -subcomponent로 선언 :
 * subcomponent로 선언하면 subcomponent는 component의 모든 의존성 제공을 받을 수 있다
 * 반대로 component는 subcomponent의 의존성을 받을 수 있다. 즉, component안에 subcomponent가
 * 캡슐화되어서 들어간다고 볼수있다. subcomponent는 component 또는 subcomponent의 subcomponent가
 * 될 수 있다.
 * 아래 예제에서는 SubModule, SubCom은 OuterCom, OuterModule로부터 의존성을 제공받을 수있다 (Material1)
 *
 * -component 상속
 * subcomponent는 서로간에 밀접한 관계를 가지기 때문에 분리가 어렵다. 상속을 이용하면 분리가 가능하다.
 */


@Subcomponent(modules = [SubModule::class])
interface SubCom {
    fun getItem(): Item

    @Subcomponent.Builder
    interface Builder {
        fun setModule(module:SubModule) : Builder
        fun build(): SubCom
    }
}

@Module
class SubModule {
    @Provides
    fun probideObj1():Object1{
        return Object1(Integer(100))
    }
}

class SubClass {
    val subComponent: SubCom

    constructor(builder: SubCom.Builder) {
        subComponent = builder.setModule(SubModule()).build()
    }

    fun provide(): Item {
        return subComponent.getItem()
    }
}

class Item @Inject constructor(
    val obj1: Object1,
    val mat1: Material1
) {
    fun info(){
        println("${obj1.no} ${mat1.id}")
    }
}


@Component(modules = [OuterModule::class])
interface OuterCom {
    fun inject(target: OutClass)
}

@Module(subcomponents = [SubCom::class])
class OuterModule {
    @Provides
    fun provideMaterial1() = Material1("100")

    @Provides
    fun provideSubClass(builder: SubCom.Builder): SubClass {
        return SubClass(builder)
    }
}

class OutClass{
    @Inject
    lateinit var subClass : SubClass
    constructor(){
        DaggerOuterCom.create().inject(this)
    }

    fun product() : Item{
        return subClass.provide()
    }
}

data class Object1(val no: Integer)
data class Material1(val id: String)

/**
 * 아래는 컴포넌트 상속의 예제
 */
@Component(modules=[ParentModule::class])
interface ParentCom{
    fun getMaterial() : ParentMaterial
}
@Module
class ParentModule{
    @Provides
    fun provideMaterial() = ParentMaterial("parent")
}

data class ParentMaterial(val name:String)

@Component(
    modules = [ChildModule::class],
    dependencies = [ParentCom::class]
)
interface ChildCom{
    fun inject(childClass: ChildClass)
}

@Module
class ChildModule{
    @Provides
    fun provideMaterial() = ChildMaterial(Integer(100))
}

data class ChildMaterial(val no:Integer)

class ChildClass{
    @Inject
    lateinit var parentMaterial: ParentMaterial

    @Inject
    lateinit var childMaterial: ChildMaterial
}
