package assetFile

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import structure.Wall
import structure.CustomWallStructure
import structure.Define
import structure.RandomNoise


class DifficultyAssetReaderKtTest {
    //todo
    //private val simpleLines ="""
    //# version, must be 1.0 currently
    //version: 1.0

    //# path of the Song
    //path: /etc/test

    //# bpm of the Song
    //bpm: 100

    //# njs you want to work with (used for timing)
    //njsOffset: 2

    //# Commands, Specify the Walls you want to create
    //# Syntax Beat(check mm for  that):Name
    //# Example Wall, remove
    //10.0: Floor
    //20.0: Ceiling
    //""".trimIndent()

    //@Before
    //fun prepAssetFile(){
    //    val s1 = SavedWallStructure("Floor", listOf(Wall(-1.0,1.0,2.0,0.0,0.0,0.0)))
    //    val s2 = SavedWallStructure("Ceiling", listOf(Wall(-1.0,1.0,2.0,0.0,4.0,0.0)))
    //    AssetFileAPI.assetFile =  AssetFile(1.0,"", arrayListOf(s1,s2))
    //}

    //@Test
    //fun readDifficultyAsset(){
    //    val actual = assetFile.readDifficultyAsset(simpleLines)

    //    val list = AssetFileAPI
    //        .assetFile()
    //        .savedStructures
    //        .filter { it.structureList.isEmpty() }
    //        .map { it.toCustomWallStructure() }

    //    list[0].beat = 10.0
    //    list[1].beat = 20.0

    //    val expected = DifficultyAsset(100.0,2.0,"/etc/test",ArrayList(list))
    //    assertEquals(expected, actual)
    //}


    //@Test
    //fun readStructures() {
    //    val structureList = mutableListOf(
    //        "0" to "Floor",
    //        "0" to "Ceiling"
    //    )
    //    val expected = ArrayList(AssetFileAPI.assetFile().savedStructures.map { it.toCustomWallStructure() })
    //    val actual = assetFile.readStructures(structureList)
    //    assertEquals(expected, actual)
    //}
    //@Test
    //fun readAdvancedStructure() {
    //    val structureList = mutableListOf(
    //        "0" to "Floor",
    //        "mirror" to "5",
    //        "time" to "true",
    //        "1" to "RandomNoise",
    //        "mirror" to "6",
    //        "amount" to "5"
    //    )
    //    val w1 = CustomWallStructure("Floor")
    //    w1.walls().add(Wall(-1.0,1.0,2.0,0.0,0.0,0.0))
    //    w1.mirror = 5
    //    w1.time = true

    //    val w2 = RandomNoise()
    //    w2.beat = 1.0
    //    w2.amount = 5
    //    w2.mirror = 6

    //    val expected = arrayListOf(w1,w2)
    //    val actual = assetFile.readStructures(structureList)
    //    assertEquals(expected, actual)
    //}

    //@Test
    //fun readDefineStructure() {
    //    val structureList = mutableListOf(
    //        "0" to "Define",
    //        "structures" to "RandomNoise,Floor",
    //        "mirror" to "5",
    //        "time" to "true",
    //        "amount" to "6"
    //    )
    //    val w2 = RandomNoise()
    //    w2.amount = 5

    //    val w1 = Define()
    //    w1.structures = listOf(w2)
    //    w1.mirror = 5
    //    w1.time = true


    //    val expected = arrayListOf(w1)
    //    val actual = assetFile.readStructures(structureList)
    //    assertEquals(expected, actual)
    //    assertNotEquals((expected[0].structures[0] as RandomNoise).amount, ((actual[0] as Define).structures[0] as RandomNoise).amount)
    //}

    @Test
    fun findStructure() {
    }

    @Test
    fun readWallStructOptions() {
    }

    @Test
    fun fillProperty() {
    }

    @Test
    fun parseAssetString() {
    }
}