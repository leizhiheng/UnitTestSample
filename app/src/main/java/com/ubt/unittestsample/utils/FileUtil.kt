package com.ubt.unittestsample.utils

import java.io.*

object FileUtil {
    fun readFile(filePath: String?, charsetName: String?): StringBuilder? {
        val file = File(filePath)
        val fileContent = StringBuilder("")
        if (file == null || !file.isFile) {
            return null
        }
        var reader: BufferedReader? = null
        try {
            val `is` = InputStreamReader(FileInputStream(file), charsetName)
            reader = BufferedReader(`is`)
            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                fileContent.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return fileContent
    }
}