package com.jeonhoeun.dagger2.study08

import org.junit.Test

class Study_08_Multi_Binding {
    @Test
    fun intoSetTest(){
        val comp = DaggerIntoSetComponent.create()
        val cls = IntoSetClass()
        comp.inject(cls)

        cls.strs.forEach {
            println(it)
        }
    }

    @Test
    fun intoMapTest(){
        val comp = DaggerIntoMapComponent.create()
        val cls = IntoMapClass()

        comp.inject(cls)

        cls.map1.toList().forEach {
            println("${it.first} ${it.second}")
        }

        cls.map2.toList().forEach {
            println("${it.first.name} ${it.second}")
        }

        println("${cls.map2.get(IntoMapClass::class.java)} ${cls.map2.get(IntoSetClass::class.java)}")
    }
}