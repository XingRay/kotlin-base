package com.github.xingray.kotlinbase.regex

object RegexUtil {
    val ipLineRegex = Regex("""inet (\d+\.\d+\.\d+\.\d+)""")
    val usbRegex = Regex("""^[a-zA-Z0-9]+$""")
    val ipv4Regex = Regex("""^\d+\.\d+\.\d+\.\d+:\d+$""")
    val mmssRegex = Regex("""\d{2}:\d{2}""")
}