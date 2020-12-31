package interpreter

import structure.WallStructure
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible



val wsProperties = wsProperties()
fun wsProperties(): List<String>{
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

fun main(){
    println(wsProperties())
}

