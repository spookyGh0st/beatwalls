package compiler.wallStructFactory

import structure.EmptyWallStructure
import structure.Interface
import structure.Line
import structure.WallStructure
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.*
import kotlin.reflect.jvm.reflect

abstract class StructFactory {
    abstract val structClass: KClass<out WallStructure>
    val structure: WallStructure by lazy { structClass.createInstance() }
    private val properties : MutableList<KProperty1<WallStructure,Double>> = mutableListOf()
    val interfaces : MutableList<String> = mutableListOf()

    fun generate(
        interfaces: List<Interface>,
        customStructFactories: List<CustomStructFactory>
    ): WallStructure {
        TODO()
    }

    fun applyInterfaces(i: Interface){
        val interfaceProperties = i::class.declaredMemberProperties.filter { it.isLateinit }
        println(interfaceProperties)
    }
}


class InterfaceStructFactory(override val structClass: KClass<out WallStructure>): StructFactory()
class CustomStructFactory(override val structClass: KClass<out WallStructure>): StructFactory()
class NormalStructFactory(override val structClass: KClass<out WallStructure>): StructFactory()
