package interpreter

import assetFile.BwDouble
import assetFile.findProperty
import assetFile.writeProperty
import structure.Structure
import structure.Wall
import structure.WallStructure
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf





