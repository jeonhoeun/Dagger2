package com.jeonhoeun.dagger2.study03

import com.jeonhoeun.dagger2.study03.di.DaggerBuilderSampleComponent
import com.jeonhoeun.dagger2.study03.di.DaggerFactorySampleComponent
import com.jeonhoeun.dagger2.study03.di.DaggerProvisionAndMemberInjectionComponent
import com.jeonhoeun.dagger2.study03.di.ProvisionAndMemberInjectionModule
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

class Study_03_Component_Advanced {
    @Test
    fun `03-01 프로비전 메소드로 값을 받을 수있다`(){
        val provisionAndMemberInjectionComponent = DaggerProvisionAndMemberInjectionComponent.create()

        val int = provisionAndMemberInjectionComponent.getInteger()

        Assert.assertSame(100,int.toInt())
    }

    @Test
    fun `03-02 멤버 인젝션 메소드로 멤버인젝션이가능하다`(){
        val target = Target()
        val provisionAndMemberInjectionComponent = DaggerProvisionAndMemberInjectionComponent.create()
        provisionAndMemberInjectionComponent.inject(target)

        Assert.assertSame(100,target.intValue.toInt())
    }

    @Test // 필드 주입방식
    fun `03-03 멤버 인젝터를 이용하여 멤버 인젝션이 가능하다 이를 필드 주입이라한다`(){
        val target = Target()
        val provisionAndMemberInjectionComponent = DaggerProvisionAndMemberInjectionComponent.create()
        val injector = provisionAndMemberInjectionComponent.getInjector()
        injector.injectMembers(target)

        Assert.assertSame(100,target.intValue.toInt())
    }

    @Test // 생성자 주입방식
    fun `03-04 생성자 주입방식`(){
        val component = DaggerProvisionAndMemberInjectionComponent.create()
        val instance = component.getConstructorSample()
        Assert.assertSame("String",instance.name)
        Assert.assertSame(100,instance.number.toInt())
    }

    @Test
    fun `03-05 상속된 클래스의 경우에 대한 멤버 인젝션`(){
        val component = DaggerProvisionAndMemberInjectionComponent.create()

        val parent = Parent()
        component.injectParent(parent)
        Assert.assertNotNull(parent.typeA)

        val me = Me()
        component.injectParent(me)
        Assert.assertNotNull(me.typeA)
        try{
            Assert.assertNull(me.typeB)
        }catch (e:Exception){
            print("not initialized")
        }

        val me2 = Me()
        component.injectMe(me2)
        Assert.assertNotNull(me2.typeA)
        Assert.assertNotNull(me2.typeB)

        val child = Child()
        component.injectParent(child)
        Assert.assertNotNull(child.typeA)
        try{
            Assert.assertNull(child.typeB)
        }catch (e:Exception){
            print("not initialized")
        }
        try{
            Assert.assertNull(child.typeC)
        }catch (e:Exception){
            print("not initialized")
        }

        val child2 = Child()
        component.injectMe(child2)
        Assert.assertNotNull(child2.typeA)
        Assert.assertNotNull(child2.typeB)
        try{
            Assert.assertNull(child2.typeC)
        }catch (e:Exception){
            print("not initialized")
        }

        val child3 = Child()
        component.injectChild(child3)
        Assert.assertNotNull(child3.typeA)
        Assert.assertNotNull(child3.typeB)
        Assert.assertNotNull(child3.typeC)
    }

    /**
     * 컴포넌트에는 @Component.Builder , @Component.Factory을 선언하면 각각 빌더 , 팩토리를 이용할 수 있으며
     * 둘다 없으면 자동으로 자동으로 만들어준다.
     */
    @Test
    fun `03-06 컴포넌트 객체 생성 방식1 - 빌더패턴`(){
        val component = DaggerBuilderSampleComponent.builder().build()
        Assert.assertNotNull(component)
        Assert.assertEquals(100,component.getInt().toInt())

        val module = ProvisionAndMemberInjectionModule()
        module.intValue= Integer(300)


        val component2 = DaggerBuilderSampleComponent.builder().setModule(module).build()
        Assert.assertEquals(300,component2.getInt().toInt())
    }

    @Test
    fun `03-07 컴포넌트 객체 생성방식2 - 팩토리패턴`(){
        val module = ProvisionAndMemberInjectionModule()
        module.intValue= Integer(500)
        val component = DaggerFactorySampleComponent.factory().newComponent(module)
        Assert.assertNotNull(component)
        Assert.assertEquals(500,component.getInt().toInt())
    }
}



class Target{
    @Inject
    lateinit var intValue:Integer // 주의!! 멤버명이 예약어 ( int ,float, long 등 ) 이면 Dagger빌드가 제대로 안된다.
}

// 생성자에 @Inject를 붙이면 생성자 주입이 가능하며 생성자 파라미터는 컴포넌트로부터 공급받는
class ConstructorSample @Inject constructor(
    val name:String,
    val number:Integer
)

open class Parent{
    @Inject
    lateinit var typeA : A
}

open class Me : Parent(){
    @Inject
    lateinit var typeB : B
}

class Child : Me(){
    @Inject
    lateinit var typeC : C
}

class A @Inject constructor()
class B @Inject constructor()
class C @Inject constructor()