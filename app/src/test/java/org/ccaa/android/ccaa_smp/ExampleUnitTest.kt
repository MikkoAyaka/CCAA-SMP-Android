package org.ccaa.android.ccaa_smp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val macBookPro = MacBookPro()
        val upgradeMacBookPro = UpgradeMacBookPro(macBookPro)
        println("""
            ${upgradeMacBookPro.getCost()}
            ${upgradeMacBookPro.getDesc()}
            ${upgradeMacBookPro.abab()}
        """.trimIndent())
    }
}
interface MacBook{
    fun getCost() : Int
    fun getDesc() : String
    fun getProdDate() : String
    fun abab()
}
class MacBookPro : MacBook{
    override fun getCost(): Int = 10000

    override fun getDesc(): String = "MacBook Pro"

    override fun getProdDate(): String = "Late 2011"

    override fun abab(){
        println("abababab")
    }
}
class UpgradeMacBookPro(private val macBook: MacBook) : MacBook by macBook{
    override fun getCost(): Int = macBook.getCost() + 219
    override fun getDesc(): String = macBook.getDesc()+",+1G Mem"
}