package com.github.xingray.kotlinbase.ext

// 字符串转义， 主要用于在命令行中输出字符串作为命令参数
fun String.escape(): String {
    return this.replace(" ", "\\ ")
        .replace("\"", "\\\"")
        .replace("&", "\\&")
        .replace("|", "\\|")
        .replace("(", "\\(")
        .replace(")", "\\)")
        .replace("<", "\\<")
        .replace(">", "\\>")
        .replace(";", "\\;")
        .replace("`", "\\`")
        .replace("'", "\\'")
        .replace("!", "\\!")
        .replace("$", "\\$")
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("?", "\\?")
        .replace("*", "\\*")
        .replace("#", "\\#")
        .replace("=", "\\=")
        .replace("%", "\\%")
}