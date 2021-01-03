package interpreter

import assetFile.*
import assetFile.bwDouble
import assetFile.bwDoubleOrNull
import assetFile.bwInt
import assetFile.bwIntOrNull
import structure.CustomStructInterface
import structure.Structure
import structure.helperClasses.ColorMode
import structure.helperClasses.Point
import structure.helperClasses.RotationMode
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf

@OptIn(ExperimentalStdlibApi::class)
class ExpressionParser(val ws: Structure, val bw: Beatwalls) {
   fun parse(tp: TokenPair){
      val p: KProperty1<out Structure, *>? = property(ws, tp.k)
      if (p == null && ws is CustomStructInterface) return ExpressionParser(ws.superStructure, bw).parse(tp)
      if (p == null){ bw.error(tp.file, tp.line, "Property ${tp.k} does not exist"); return }

      val ss = ws.structureState
      val value: Any? = when (p.returnType) {
         typeOf<Boolean>() -> tp.v.toBoolean()
         typeOf<Int>() -> tp.v.toIntOrNull()
         typeOf<Double>() -> tp.v.toDoubleOrNull()
         typeOf<String>() -> tp.v
         typeOf<List<String>>() -> tp.v.toLowerCase().replace(" ","").split(',')
         typeOf<Point>() -> tp.v.toPoint()
         typeOf<ColorMode>() -> tp.v.toColorMode()
         typeOf<RotationMode>() -> tp.v.toRotationMode()
         typeOf<BwDouble>()  -> bwDouble(tp.v,ss)
         typeOf<BwDouble?>() -> bwDoubleOrNull(tp.v,ss)
         typeOf<BwInt>()  -> bwInt(tp.v,ss)
         typeOf<BwInt?>() -> bwIntOrNull(tp.v,ss)
         else -> bw.error(tp.file,tp.line, "Unknown type: ${p.returnType}")
      }
      writeProb(ws, p, value)
   }

   // wrapper of the property functions
   // has the define-logic of WallStructures
   private fun writeProb(struct: Structure, p: KProperty1<out Structure, *>, value: Any?) {
      when (p) {
         !is KMutableProperty<*> -> error("Property is is not mutable")
         else -> p.setter.call(struct, value)
      }
   }

   // gets the property of a Structure of a given name
   private fun property(struct: Structure, name: String): KProperty1<out Structure, *>? {
      val props = struct::class.memberProperties
      val p = props.find { it.name.equals(name, ignoreCase = true) }?: return null
      p.isAccessible = true
      return p
   }
}