package com.github.xingray.kotlinbase.net

data class IPV4(val address0: Int, val address1: Int, val address2: Int, val address3: Int) {

    init {
        if (address0 < 0 || address0 > 255) {
            throw IllegalArgumentException("illegal ip filed, address0 :${address0}")
        }

        if (address1 < 0 || address1 > 255) {
            throw IllegalArgumentException("illegal ip filed, address1 :${address1}")
        }

        if (address2 < 0 || address2 > 255) {
            throw IllegalArgumentException("illegal ip filed, address2 :${address2}")
        }

        if (address3 < 0 || address3 > 255) {
            throw IllegalArgumentException("illegal ip filed, address3 :${address3}")
        }
    }

    fun toTextString(): String {
        return "${address0}.${address1}.${address2}.${address3}"
    }
}

fun String.parseIPV4(): IPV4 {
    val ipSegments = this.split(".").map { it.toInt() }

    if (ipSegments.size == 4) {
        val ip0 = ipSegments[0]
        val ip1 = ipSegments[1]
        val ip2 = ipSegments[2]
        val ip3 = ipSegments[3]

        return IPV4(ip0, ip1, ip2, ip3)
    } else {
        throw java.lang.IllegalArgumentException("illegal ip string:${this}")
    }
}