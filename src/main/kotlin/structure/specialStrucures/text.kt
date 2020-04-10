package structure.specialStrucures

import structure.helperClasses.SpookyWall



enum class Letters(val l: List<SpookyWall>){
    F(listOf<SpookyWall>(
        w(-0.5, 0.0, 0.2, 2.0, 0.0, 0.0),
        w(-0.3, 0.0, 0.4, 0.2, 1.0, 0.0),
        w(-0.3, 0.0, 0.7, 0.2, 1.8, 0.0)
    )),
    I(listOf<SpookyWall>(
        w(-0.1, 0.0, 0.2, 2.0, 0.0, 0.0)
    )),

    G ( listOf<SpookyWall>(
        w(-0.5, 0.0, 0.2, 2.0, 0.0, 0.0),
        w(-0.3, 0.0, 0.7, 0.3, 0.0, 0.0),
        w(0.4, 0.0, 0.1, 0.5, 0.2, 0.0),
        w(0.0, 0.0, 0.5, 0.2, 0.8, 0.0)
    )),

    H ( listOf<SpookyWall>(
        w(-0.5, 0.0, 0.2, 2.0, 0.0, 0.0),
        w(0.3, 0.0, 0.2, 2.0, 0.0, 0.0),
        w(-0.3, 0.0, 0.6, 0.2, 0.9, 0.0)
    )),

    T ( listOf<SpookyWall>(
        w(-0.1, 0.0, 0.2, 1.8, 0.0, 0.0),
        w(-0.5, 0.0, 1.0, 0.2, 1.8, 0.0)
    ))
}


typealias w = SpookyWall
