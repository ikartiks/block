package com.example.block.api

import java.util.Scanner

/**
 * Created to read responses from a file
 */
object FileReader {

    /**
     * Created to read responses from a file
     */
    fun readStringFromFile(fileName: String): String {

        val sb = StringBuilder()
        try {
            val myReader = Scanner(this.javaClass.classLoader!!.getResourceAsStream(fileName))
            while (myReader.hasNextLine()) {
                val data: String = myReader.nextLine()
                sb.append(data)
            }
            myReader.close()
        } catch (e: Exception) {
            println("An error occurred.")
            e.printStackTrace()
        }
        return sb.toString()
    }
}
