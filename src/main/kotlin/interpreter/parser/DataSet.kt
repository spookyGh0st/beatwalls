package interpreter.parser

import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.WallStructure
import kotlin.collections.HashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

// Line Parser parses each line and build wallstructures from them
// it assumes everything is already lowercase
data class DataSet(
    // stores all baseStructures as KClass, defaults to all
    val wsFactories: HashMap<String, WsFactory> = baseStructures(),

    // stores all user-defined Interfaces (and default interface)
    val interfaces: HashMap<String, BwInterface> = hashMapOf("default" to BwInterface()),

    // stores all user-defined Constants
    val constantList: HashMap<String, Constant> = hashMapOf(),

    // stores all user-defined Functions
    val functionList: HashMap<String, Function> = hashMapOf(),

    // stores all properties of all WallStructures (subclasses as well)
    val wsPropsNames: List<String> = bwProperties()
)

fun DataSet.inKeys(s:String) =
    s in wsFactories.keys || s in interfaces.keys || s in constantList.keys || s in functionList.keys || s in wsPropsNames

// its not unchecked you bongo compiler
@Suppress("UNCHECKED_CAST")
fun bwProperties(): List<String> {
    val wsClass = WallStructure::class
    // this is needed, since sealedSubclasses does not return nested subclasses
    val wsSClasses = wsClass.recursiveWsClasses()
    val props =  wsClass.memberProperties + wsSClasses.flatMap { it.declaredMemberPropertiesDelegates }
    return props.map { it.name.toLowerCase().trim() }
}

val <E: Any> KClass<E>.declaredMemberPropertiesDelegates : Collection<KProperty1<E, Any?>>
get()  {
    val props: Collection<KProperty1<E, Any?>> = this.declaredMemberProperties
    props.forEach { it.isAccessible = true }
    return props
}

fun<E:Any> KClass<E>.recursiveWsClasses(): List<KClass<out E>> {
    return if (this.isSealed)
        this::sealedSubclasses.get().flatMap { it.recursiveWsClasses() }
    else
        listOf(this)
}

/**
 * return WallStructFactories of all the base-wall-structures
 */
fun baseStructures(): HashMap<String, WsFactory> {
    // this is needed, since sealedSubclasses does not return nested subclasses
    val l = WallStructure::class.recursiveWsClasses()
    // creates the HashMap we will fill later
    val hm = hashMapOf<String, WsFactory>()
    // creates a WsFactory from each hm
    l.forEach {
        val n = it.simpleName?.toLowerCase() ?: ""
        val v = { it.createInstance() }
        hm[n] = WsFactory(v)
    }
    return hm
}
