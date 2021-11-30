package com.jeonhoeun.dagger2.study02

import com.jeonhoeun.dagger2.study02.di.DaggerInheritanceComponent
import com.jeonhoeun.dagger2.study02.di.DaggerNullableComponent
import com.jeonhoeun.dagger2.study02.di.DaggerProvideInstanceByModuleComponent
import com.jeonhoeun.dagger2.study02.di.DaggerProvideSameTypeTwiceErrorComponent
import org.junit.Assert
import org.junit.Test

class Study_02_Module_Advanced {

    /**
     * 하나의 모듈에서 동일한 타입을 중복 제공하면 빌드오류가 나는 예제임
     * Study02_Di / ProvideStringTwiceErrorExampleModule 안의 주석을 제거하면 빌드오류 재현됨
     */

    @Test
    fun `02-01 동일한 타입을 제공하면 빌드 오류`(){
        val provideSameTypeTwiceErrorComponent = DaggerProvideSameTypeTwiceErrorComponent.create()
        Assert.assertEquals("StringOne",provideSameTypeTwiceErrorComponent.getString())
    }

    /**
     * 모듈에서 인스턴스를 제공하는 예제로, 아래와같은 경우 getInstance를 호출할때마다 새로은 인스턴스를 받으므로
     * 인스턴스별 hash값은 다르다
     */
    @Test
    fun `02-02 모듈에서 Instance를 제공하는 예제로, 생성자 파라미터를 자체 공급`(){
        val provideInstanceComponent = DaggerProvideInstanceByModuleComponent.create()
        val instance = provideInstanceComponent.getInstance()
        val instance2 = provideInstanceComponent.getInstance()

        Assert.assertEquals(instance.num,100)
        Assert.assertEquals(instance.name,"Name")
        Assert.assertNotEquals(instance,instance2)
    }

    /**
     * 모듈에서 Nullable을 제공하려면 @Nullable을 이용하면 된다.
     * 다만 코들린에서는 ? (옵셔널)을 이용해도 된다.
     */

    @Test
    fun `02-03 모듈에서는 Nullable을 통하여 Nullable을 제공할 수 있다`(){
        val nullableComponent = DaggerNullableComponent.create()
        val nullableInteger = nullableComponent.getNullableInteger()
        Assert.assertNull(nullableInteger)
    }


    /**
     * 모듈은 상속 가능하다.
     */

    @Test
    fun `02-04 모듈은 모둘선언시 includes 키워드로 상속 가능하다`(){
        val inheritanceComponent = DaggerInheritanceComponent.create()
        val instance = inheritanceComponent.getInstance()
        Assert.assertSame(100,instance.num)
        Assert.assertSame("Parent",instance.name)
    }
}