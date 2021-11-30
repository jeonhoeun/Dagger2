package com.jeonhoeun.dagger2.study01

import com.jeonhoeun.dagger2.study01.di.DaggerSimpleComponent
import org.junit.Assert
import org.junit.Test

class Study_01_Module_And_Component {
    @Test
    fun `01 simple module and component`(){

        // component 는 builder 또는 직접 create 가능하다

        //builder 이용방법
        val simpleComponent = DaggerSimpleComponent.builder().build()
        Assert.assertEquals("SimpleString",simpleComponent.getString())

        //create 방법
        val simpleComponent2 = DaggerSimpleComponent.create()
        Assert.assertEquals("SimpleString",simpleComponent2.getString())
    }
}