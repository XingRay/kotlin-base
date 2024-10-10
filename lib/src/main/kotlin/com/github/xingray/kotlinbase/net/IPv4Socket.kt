package com.github.xingray.coinfarmer.kotlin.net

data class IPv4Socket(val ipV4: IPV4, val port: Int) {

    companion object {
        fun parse(text: String): IPv4Socket {
            val (ipPart, portPart) = text.split(":")
            val port = portPart.toInt()
            return IPv4Socket(ipPart.parseIPV4(), port)
        }
    }

    fun toTextString(): String {
        return "${ipV4.toTextString()}:$port"
    }
}