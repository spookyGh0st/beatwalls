package interpreter.property.functions

import net.objecthunter.exp4j.function.Function


class BwSwitch2: Function("switch2",2){
    var counter: Int = 0
    override fun apply(vararg args: Double): Double {
        return args[counter++ % args.size ]
    }
}
class BwSwitch3: Function("switch3",3){
    var counter: Int = 0
    override fun apply(vararg args: Double): Double {
        return args[counter++ % args.size ]
    }
}
class BwSwitch4: Function("switch4",4){
    var counter: Int = 0
    override fun apply(vararg args: Double): Double {
        return args[counter++ % args.size ]
    }
}

class BwSwitch5: Function("switch5",4){
    var counter: Int = 0
    override fun apply(vararg args: Double): Double {
        return args[counter++ % args.size ]
    }
}
