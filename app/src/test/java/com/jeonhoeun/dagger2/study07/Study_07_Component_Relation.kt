package com.jeonhoeun.dagger2.study07

import org.junit.Test

class Study_07_Component_Relation {
    @Test
    fun subcomponentTest(){
        val outerClass = OutClass()
        println("${outerClass.product().mat1} ${outerClass.product().obj1}");
    }

    @Test
    fun expendComponentTest(){
        val childClass = ChildClass()

        val parentcom = DaggerParentCom.create()
        val childcom = DaggerChildCom.builder().parentCom(parentcom).build()

        childcom.inject(childClass)

        print("${childClass.childMaterial.no} ${childClass.parentMaterial.name}")
    }
}