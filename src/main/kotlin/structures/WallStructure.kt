package structures

import kotlin.reflect.KProperty

sealed class WallStructure() {

    /**saves the name of the class directly to the name property */
    open val name = this::class.simpleName ?: String()

    abstract fun getWalls()

    fun remove(wall: Wall) {
        wallList.remove(wall)
    }

    fun remove(wall: Collection<Wall>) {
        wallList.removeAll(wall)
    }

    fun add(wall: Wall) {
        wallList.add(wall)
    }

    fun add(wall: Collection<Wall>) {
        wallList.addAll(wall)
    }


    val wallList = arrayListOf<Wall>()


    /** Options are used to set the Parameters */
    val options = arrayListOf<kotlin.String>()

    /** a Flag, can be true of false */
    inner class Boolean(private val default:kotlin.Boolean= false) {
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.Boolean {
            val ref = getOptionList(property.name)
            return if (options.any{ ref.contains(it)})
                !default
            else
                default
        }
    }

    /** Options hold value of Type Int */
    inner class Int(private val default: kotlin.Int?= null){
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.Int?{
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
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.Double?{
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
        operator fun getValue(thisRef: WallStructure, property: KProperty<*>): kotlin.String?{
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
    fun getOptionList(string: kotlin.String): ArrayList<kotlin.String>{
        val ref = arrayListOf<kotlin.String>()
        ref.add(string.toLowerCase().prependIndent("--"))
        ref.add(string
            .toLowerCase()
            .prependIndent("-")
            .removeRange(2,string.lastIndex+2)
        )
        return ref
    }
}


class TestStructure:WallStructure(){
    val mirror by Boolean(true)
    val amount by Int(12)
    val text by String("abc")
    override fun getWalls() {
        add(Wall(0.0,1.0,0.0,0.0,0.0,0.0))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
fun main(){
    val a = TestStructure()
    a.options.addAll(listOf("bla","-m","--amount","123","hallowelt","--tet","'123'"))
    println(a.mirror)
    println(a.amount)
    println(a.text)
    val b = a.wallList
    b.add(Wall(0.0,1.0,0.0,0.0,0.0,0.0))
    println(a.wallList.size)
}

