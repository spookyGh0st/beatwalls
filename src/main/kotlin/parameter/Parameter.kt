package parameter

class Parameter() : CommandParser() {
    val beat by Double(0.0)
    val fast by Boolean()
    val hyper by Boolean()
    val mirror by Boolean()
    val verticalMirror by Boolean(short = false)
    val horizontalMirror by Boolean(short = false)
    val scale by Double(1.0)
    val verticalScale by Double(1.0)
    val grounder by Double(0.0)
    val extender by Double(1.0)
    val structureList by WallStructureList()

    constructor(c:Command):this(){
        options.addAll(c.command.split(" "))
    }
}

