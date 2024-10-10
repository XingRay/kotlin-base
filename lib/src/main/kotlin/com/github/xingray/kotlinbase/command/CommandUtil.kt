package com.github.xingray.kotlinbase.command

import com.github.xingray.kotlinbase.command.annotations.Command
import com.github.xingray.kotlinbase.command.annotations.KeyValueLinker
import com.github.xingray.kotlinbase.command.annotations.ListValueSeparator
import com.github.xingray.kotlinbase.command.annotations.NoCommandKey
import java.io.File
import java.lang.reflect.Field
import java.util.*

object CommandUtil {
    fun splitCmd(command: String): List<String> {
        require(!command.isEmpty()) { "Empty command" }

        val st = StringTokenizer(command)
        val cmdArray = mutableListOf<String>()
        var i = 0
        while (st.hasMoreTokens()) {
            cmdArray.add(st.nextToken())
            i++
        }

        return cmdArray
    }

    @Throws(IllegalAccessException::class)
    fun commandToString(command: Any): String {
        val executorAnnotation: Command = command.javaClass.getAnnotation(Command::class.java) ?: throw IllegalArgumentException("command must have annotation @Executor")
        val executor: String = executorAnnotation.value

        val stringBuilder = StringBuilder()
        stringBuilder.append(executor).append(' ')

        val fields = command.javaClass.declaredFields
        if (fields.size == 0) {
            return stringBuilder.toString()
        }
        for (field in fields) {
            // todo get field value by getter
            field.isAccessible = true
            val value = field[command] ?: continue

            val noCommandKeyAnnotation: NoCommandKey = field.getAnnotation(NoCommandKey::class.java)

            val fieldType = field.type
            if (fieldType == Boolean::class.java) {
                if (value as Boolean) {
                    appendCommandKey(stringBuilder, field.name)
                }
            } else if (fieldType == Int::class.java) {
                appendCommandKey(stringBuilder, field.name)
                stringBuilder.append('=').append(value)
            } else if (fieldType == String::class.java) {
                if (noCommandKeyAnnotation == null) {
                    appendCommandKey(stringBuilder, field.name)
                    stringBuilder.append('=')
                }
                stringBuilder.append(value)
            } else if (fieldType == MutableList::class.java) {
                val list = value as List<*>
                if (list.isEmpty()) {
                    continue
                }
                if (noCommandKeyAnnotation == null) {
                    appendCommandKey(stringBuilder, field.name)
                    stringBuilder.append("=")
                }
                var index = 0
                for (e in list) {
                    if (index > 0) {
                        stringBuilder.append(File.pathSeparatorChar)
                    }
                    stringBuilder.append(e.toString())
                    index++
                }
            }
            stringBuilder.append(' ')
        }

        return stringBuilder.toString()
    }

    @Throws(IllegalAccessException::class)
    fun commandToStringList(command: Any): List<String> {
        val commandClass: Class<*> = command.javaClass
        val executorAnnotation: Command = commandClass.getAnnotation(Command::class.java) ?: throw IllegalArgumentException("command must have annotation @Executor")

        val defaultKeyValueLinker = getFieldKeyValueLinker(commandClass.getAnnotation(KeyValueLinker::class.java), "=")

        val executor: String = executorAnnotation.value

        val stringList: MutableList<String> = ArrayList()
        stringList.add(executor)

        val fields = commandClass.declaredFields
        if (fields.size == 0) {
            return stringList
        }
        for (field in fields) {
            // todo get field value by getter
            field.isAccessible = true
            val value = field[command] ?: continue

            val noCommandKeyAnnotation: NoCommandKey = field.getAnnotation(NoCommandKey::class.java)

            val fieldType = field.type
            var stringBuilder = StringBuilder()
            if (fieldType == Boolean::class.java) {
                if (value as Boolean) {
                    appendCommandKey(stringBuilder, field.name)
                }
            } else if (fieldType == Int::class.java) {
                appendCommandKey(stringBuilder, field.name)
                stringBuilder = handleKeyAndLinker(field, defaultKeyValueLinker, stringList, stringBuilder)
                stringBuilder.append(value)
            } else if (fieldType == String::class.java) {
                if (noCommandKeyAnnotation == null) {
                    appendCommandKey(stringBuilder, field.name)
                    stringBuilder = handleKeyAndLinker(field, defaultKeyValueLinker, stringList, stringBuilder)
                }
                stringBuilder.append(value)
            } else if (fieldType == MutableList::class.java) {
                var separator: String? = null
                val list = value as List<*>
                if (list.isEmpty()) {
                    continue
                }
                if (noCommandKeyAnnotation == null) {
                    appendCommandKey(stringBuilder, field.name)
                    stringBuilder = handleKeyAndLinker(field, defaultKeyValueLinker, stringList, stringBuilder)
                }
                var index = 0
                for (e in list) {
                    if (index > 0) {
                        if (separator == null) {
                            separator = getListValueSeparator(field.getAnnotation(ListValueSeparator::class.java), File.pathSeparator)
                        }
                        stringBuilder.append(separator)
                    }
                    stringBuilder.append(e.toString())
                    index++
                }
            }
            stringList.add(stringBuilder.toString())
        }

        return stringList
    }

    private fun handleKeyAndLinker(field: Field, defaultKeyValueLinker: String, stringList: MutableList<String>, stringBuilder: StringBuilder): StringBuilder {
        var stringBuilder = stringBuilder
        val keyValueLinker = getFieldKeyValueLinker(field.getAnnotation(KeyValueLinker::class.java), defaultKeyValueLinker)
        if (keyValueLinker.isBlank()) {
            stringList.add(stringBuilder.toString())
            stringBuilder = StringBuilder()
        } else {
            stringBuilder.append(keyValueLinker)
        }
        return stringBuilder
    }

    private fun getFieldKeyValueLinker(keyValueLinkerAnnotation: KeyValueLinker?, defaultValue: String): String {
        val valueLinker = if (keyValueLinkerAnnotation != null) {
            keyValueLinkerAnnotation.value
        } else {
            defaultValue
        }
        return valueLinker
    }

    private fun getListValueSeparator(listValueSeparator: ListValueSeparator?, defaultValue: String): String {
        val separator = if (listValueSeparator != null) {
            listValueSeparator.value
        } else {
            defaultValue
        }
        return separator
    }

    @Throws(IllegalAccessException::class)
    fun commandToStringArray(command: Any): Array<String> {
        val stringList = commandToStringList(command)
        return stringList.toTypedArray<String>()
    }

    private fun appendCommandKey(stringBuilder: StringBuilder, name: String) {
        stringBuilder.append("--")
        val length = name.length
        var i = 0
        while (i < length) {
            val pieceStart = i
            var pieceEnd = i
            var ch = name[pieceEnd]
            while (ch < 'A' || ch > 'Z') {
                pieceEnd++
                if (pieceEnd >= length) {
                    stringBuilder.append(name, pieceStart, pieceEnd)
                    return
                }
                ch = name[pieceEnd]
            }
            stringBuilder.append(name, pieceStart, pieceEnd)
            stringBuilder.append('-')
            stringBuilder.append(ch.lowercaseChar())
            i = pieceEnd + 1
        }
    }

    private fun toCommandKey(name: String): String {
        val stringBuilder = StringBuilder()
        appendCommandKey(stringBuilder, name)
        return stringBuilder.toString()
    }

    fun <T> toString(list: List<T>?, separator: Char): String {
        if (list == null || list.isEmpty()) {
            return ""
        }

        val stringBuilder = StringBuilder()
        var index = 0
        for (t in list) {
            if (index > 0) {
                stringBuilder.append(separator)
            }
            stringBuilder.append(t.toString())
            index++
        }
        return stringBuilder.toString()
    }
}
