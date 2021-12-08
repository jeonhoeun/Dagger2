package com.jeonhoeun.dagger2.study05

import com.jeonhoeun.dagger2.study05.di.*
import org.junit.Assert
import org.junit.Test

class Study_05_Named_and_Scope {
    @Test
    fun namedTest(){
        val component = DaggerUsingNamedComponent.create()
        val cls = UsingNamedClass()
        component.inject(cls)
        cls.printNames()
    }

    @Test
    fun userDefainedQualifierTest(){
        val component = DaggerUsingUserDefinedQualifierComponent.create()
        val cls = UsingUserDefinedQualifierClass()
        component.inject(cls)
        cls.printNames()
    }

    @Test
    fun singletonTest(){
        val component = DaggerSingletonComponent.create()
        val obj1 = component.getObject()
        val obj2 = component.getObject()
        Assert.assertEquals(obj1,obj2)
    }

    @Test
    fun reusableTest(){
        val component = DaggerReusableComponent.create()
        val obj1 = component.getObject()
        val obj2 = component.getObject()
        Assert.assertEquals(obj1,obj2)
    }

    @Test
    fun userDefinedScopeTest(){
        val component = DaggerUserDefinedScopeComponent.create()
        val obj1 = component.getObject()
        val obj2 = component.getObject()
        Assert.assertEquals(obj1,obj2)
    }
}