package com.task.marvel.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {
    fun getHash(hashApi: String, keyApi: String, time: String): String {
        val MD5 = "MD5"
        val string = time + hashApi + keyApi
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(string.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }
}