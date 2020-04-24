package compiler.parser

import compiler.parser.types.BwInterface
import compiler.property.BwProperty
import org.mariuszgromada.math.mxparser.Constant
import org.mariuszgromada.math.mxparser.Function
import structure.WallStructure
import kotlin.collections.HashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
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
    val wsProperties: HashMap<String, (WallStructure) -> BwProperty> = bwProperties()
)

fun DataSet.inKeys(s:String) =
    s in wsFactories.keys || s in interfaces.keys || s in constantList.keys || s in functionList.keys || s in wsProperties.keys

// its not unchecked you bongo compiler
@Suppress("UNCHECKED_CAST")
fun bwProperties(): HashMap<String, (WallStructure) -> BwProperty> {
    //TODO this should be cleaner with better types
    // it is possinle without unchecked cast
    val wsClass = WallStructure::class
    // this is needed, since sealedSubclasses does not return nested subclasses
    val wsSClasses = wsClass.recursiveWsClasses()
    val props =  wsClass.declaredMemberPropertiesDelegates + wsSClasses.flatMap { it.declaredMemberPropertiesDelegates }

    val hm = hashMapOf<String, (WallStructure) -> BwProperty>()
    props.forEach {
        it as KProperty1<WallStructure,Any?>
        val n = it.name.toLowerCase().trim()
        val v: (WallStructure) -> BwProperty = { ref: WallStructure -> it.getDelegate(ref) as BwProperty }
        hm[n] = v
    }
    return hm
}

val <E: Any> KClass<E>.declaredMemberPropertiesDelegates : Collection<KProperty1<E, Any?>>
get()  {
    val props: Collection<KProperty1<E, Any?>> = this.declaredMemberProperties as Collection<KProperty1<E, Any?>>
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
