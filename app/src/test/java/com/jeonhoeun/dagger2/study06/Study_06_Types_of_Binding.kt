package com.jeonhoeun.dagger2.study06

import org.junit.Test

class Study_06_Types_of_Binding {
    @Test
    fun testBinds() {
        val com = DaggerBindsComponent.create()
        val parent = com.getParent()
        val child = com.getChild()

        //아래의 hash코드는 같다. (물론 @Singleton효과다)
        println("parent:${parent.hashCode()} child:${child.hashCode()}")
    }


    @Test
    fun testBindsOptionalOf() {
        val com = DaggerBindsOptionalOfComponent.create()


        val optCls = OptionalCls()
// 이상태에서는 런타임 오류발생한다.
//        println("optCls ${optCls.str.get()}")
//        println("optCls ${optCls.strP.get().get()}")
//        println("optCls ${optCls.strL.get()}")

        com.inject(optCls)
        println("optCls ${optCls.str.get()}")
        println("optCls ${optCls.strP.get().get()}")
        println("optCls ${optCls.strL.get().get()}")
    }

    @Test
    fun bindsInstanceTest(){
        val obj = Obj()
        obj.name="JJJ"
        val com = DaggerBindsInstComponent.builder().setObj(
            obj
        ).build()

        println(com.getObjs().name())
    }
}