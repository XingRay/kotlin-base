package com.github.xingray.kotlinbase.util

import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

object SystemUtil {

    fun openDirectory(directoryPath: String): String? {
        val osName = getSystemName()

        if (osName.contains("windows")) {
            try {
                Runtime.getRuntime().exec("explorer.exe $directoryPath")
                return null
            } catch (e: IOException) {
                e.printStackTrace()
                return e.message
            }
        } else {
            return "暂不支持此系统：$osName"
        }
    }

    fun getUserDirectory(): String {
        return System.getProperty("user.dir")
    }

    fun getPath(relativePath: String): String {
        return getUserDirectory() + relativePath
    }

    fun printSystem() {
        val r = Runtime.getRuntime()
        val properties = System.getProperties()
        val addr: InetAddress
        var ip: String? = null
        var hostName: String? = null
        try {
            addr = InetAddress.getLocalHost()
            ip = addr.hostAddress
            hostName = addr.hostName
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        }

        val env = System.getenv()
        val userName = env["USERNAME"] // 获取用户名
        val computerName = env["COMPUTERNAME"] // 获取计算机名
        val userDomain = env["USERDOMAIN"] // 获取计算机域名

        println("用户名:    $userName")
        println("计算机名:    $computerName")
        println("计算机域名:    $userDomain")
        println("本地ip地址:    $ip")
        println("本地主机名:    $hostName")
        println("JVM可以使用的总内存:    " + r.totalMemory())
        println("JVM可以使用的剩余内存:    " + r.freeMemory())
        println("JVM可以使用的处理器个数:    " + r.availableProcessors())
        println("Java的运行环境版本：    " + properties.getProperty("java.version"))
        println("Java的运行环境供应商：    " + properties.getProperty("java.vendor"))
        println("Java供应商的URL：    " + properties.getProperty("java.vendor.url"))
        println("Java的安装路径：    " + properties.getProperty("java.home"))
        println("Java的虚拟机规范版本：    " + properties.getProperty("java.vm.specification.version"))
        println("Java的虚拟机规范供应商：    " + properties.getProperty("java.vm.specification.vendor"))
        println("Java的虚拟机规范名称：    " + properties.getProperty("java.vm.specification.name"))
        println("Java的虚拟机实现版本：    " + properties.getProperty("java.vm.version"))
        println("Java的虚拟机实现供应商：    " + properties.getProperty("java.vm.vendor"))
        println("Java的虚拟机实现名称：    " + properties.getProperty("java.vm.name"))
        println("Java运行时环境规范版本：    " + properties.getProperty("java.specification.version"))
        println("Java运行时环境规范供应商：    " + properties.getProperty("java.specification.vender"))
        println("Java运行时环境规范名称：    " + properties.getProperty("java.specification.name"))
        println("Java的类格式版本号：    " + properties.getProperty("java.class.version"))
        println("Java的类路径：    " + properties.getProperty("java.class.path"))
        println("加载库时搜索的路径列表：    " + properties.getProperty("java.library.path"))
        println("默认的临时文件路径：    " + properties.getProperty("java.io.tmpdir"))
        println("一个或多个扩展目录的路径：    " + properties.getProperty("java.ext.dirs"))
        println("操作系统的名称：    " + properties.getProperty("os.name"))
        println("操作系统的构架：    " + properties.getProperty("os.arch"))
        println("操作系统的版本：    " + properties.getProperty("os.version"))
        println("文件分隔符：    " + properties.getProperty("file.separator"))
        println("路径分隔符：    " + properties.getProperty("path.separator"))
        println("行分隔符：    " + properties.getProperty("line.separator"))
        println("用户的账户名称：    " + properties.getProperty("user.name"))
        println("用户的主目录：    " + properties.getProperty("user.home"))
        println("用户的当前工作目录：    " + properties.getProperty("user.dir"))

        println("=============   System.getProperties()   =============")
        for ((key, value) in properties) {
            println("$key:$value")
        }

        println("=============   System.getenv()   ===============")
        for ((key, value) in env) {
            println("$key:$value")
        }
    }

    fun isRunOnWindows(): Boolean {
        val osName = getSystemName()
        return osName.contains("windows")
    }

    fun getSystemName(): String {
        return System.getProperty("os.name").lowercase(Locale.getDefault())
    }
}