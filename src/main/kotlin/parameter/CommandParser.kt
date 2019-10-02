package parameter

import structure.WallStructure
import kotlin.reflect.KProperty

abstract class CommandParser{
    /** Options are used to set the Parameters */
    val options = arrayListOf<kotlin.String>()
    /** Parses the commands */
    inner class WallStructureList {
        operator fun getValue(thisRef: Any, property: KProperty<*>): ArrayList<WallStructure> {
            TODO()
        }
    }

    /** a Flag, can be true of false */
    inner class Boolean(private val default:kotlin.Boolean= false, private val short: kotlin.Boolean= true) {
        operator fun getValue(thisRef: Any, property: KProperty<*>): kotlin.Boolean {
            val ref = getOptionList(property.name, short)
            return if (options.any{ ref.contains(it)})
                !default
            else
                default
        }
    }
    /** Options hold value of Type Int */
    inner class Int(private val default: kotlin.Int?= null){
        operator fun getValue(thisRef: Any, property: KProperty<*>): kotlin.Int?{
            val ref = getOptionList(property.name)
            if(options.none{ ref.contains(it)})
                return default
            else
                for (r in ref){
                    val index = options.indexOf(r)
                    val result = options.getOrNull(index+1)?.toIntOrNull()
                    if(result !=null)
                        return result
                }
            return null
        }
    }
    /** Options hold value of Type Double */
    inner class Double(private val default: kotlin.Double?= null){
        operator fun getValue(thisRef: Any, property: KProperty<*>): kotlin.Double?{
            val ref = getOptionList(property.name)
            if(options.none{ ref.contains(it)})
                return default
            else
                for (r in ref){
                    val index = options.indexOf(r)
                    val result = options.getOrNull(index+1)?.toDoubleOrNull()
                    if(result !=null)
                        return result
                }
            return default
        }
    }
    /** Options hold value of Type Double */
    inner class String(private val default: kotlin.String?= null){
        operator fun getValue(thisRef: Any, property: KProperty<*>): kotlin.String?{
            val ref = getOptionList(property.name)
            if(options.none{ ref.contains(it)})
                return default
            else
                for (r in ref){
                    val index = options.indexOf(r)
                    val result = options.getOrNull(index+1)
                    if(result !=null && result.startsWith('\'') && result.endsWith('\''))
                        return result.removeSurrounding("'")
                }
            return default
        }
    }
    /** gets the String list of --sfa -s for the sfa */
    fun getOptionList(string: kotlin.String, short: kotlin.Boolean = true): ArrayList<kotlin.String>{
        val ref = arrayListOf<kotlin.String>()
        ref.add(string.toLowerCase().prependIndent("--"))
        if(short)
            ref.add(string
                .toLowerCase()
                .prependIndent("-")
                .removeRange(2,string.lastIndex+2)
            )
        return ref
    }


}