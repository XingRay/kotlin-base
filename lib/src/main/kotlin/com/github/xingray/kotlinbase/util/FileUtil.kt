package com.github.xingray.kotlinbase.util

import com.github.xingray.kotlinbase.util.collection.CollectionUtil
import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

object FileUtil {

    val FILE_EXTENSION_SEPARATOR: String = "."

    fun readFile(filePath: String): String? {
        return readFile(filePath, "utf-8")
    }

    fun readFile(filePath: String, charsetName: String?): String? {
        val file = File(filePath)
        val fileContent = StringBuilder("")
        if (!file.isFile) {
            return null
        }

        var reader: BufferedReader? = null
        try {
            val `is` = InputStreamReader(FileInputStream(file), charsetName)
            reader = BufferedReader(`is`)
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                if (fileContent.toString() != "") {
                    fileContent.append("\r\n")
                }
                fileContent.append(line)
            }
            return fileContent.toString()
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            close(reader!!)
        }
    }

    fun writeFile(filePath: String, content: String?): Boolean {
        return writeFile(filePath, content, false)
    }

    fun writeFile(filePath: String, content: String?, append: Boolean): Boolean {
        if (StringUtil.isEmpty(content)) {
            return false
        }

        var fileWriter: FileWriter? = null
        try {
            makeDirs(filePath)
            fileWriter = FileWriter(filePath, append)
            fileWriter.write(content)
            return true
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            close(fileWriter!!)
        }
    }

    fun writeFile(filePath: String, contentList: List<String?>?, append: Boolean): Boolean {
        if (contentList == null || contentList.isEmpty()) {
            return false
        }

        var fileWriter: FileWriter? = null
        try {
            makeDirs(filePath)
            fileWriter = FileWriter(filePath, append)
            var i = 0
            for (line in contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n")
                }
                fileWriter.write(line)
            }
            return true
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            close(fileWriter!!)
        }
    }


    fun writeFile(filePath: String, contentList: List<String?>?): Boolean {
        return writeFile(filePath, contentList, false)
    }

    fun writeFile(filePath: String?, stream: InputStream): Boolean {
        return writeFile(filePath, stream, false)
    }

    fun writeFile(filePath: String?, stream: InputStream, append: Boolean): Boolean {
        return writeFile(if (filePath != null) File(filePath) else null, stream, append)
    }


    fun writeFile(file: File?, stream: InputStream): Boolean {
        return writeFile(file, stream, false)
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if `true`, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    fun writeFile(file: File?, stream: InputStream, append: Boolean): Boolean {
        var o: OutputStream? = null
        try {
            makeDirs(file!!.absolutePath)
            o = FileOutputStream(file, append)
            val data = ByteArray(1024)
            var length = -1
            while ((stream.read(data).also { length = it }) != -1) {
                o.write(data, 0, length)
            }
            o.flush()
            return true
        } catch (e: FileNotFoundException) {
            throw RuntimeException("FileNotFoundException occurred. ", e)
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            close(o!!)
            close(stream)
        }
    }

    /**
     * move file
     *
     * @param srcFile
     * @param destFile
     */
    fun moveFile(srcFile: File, destFile: File) {
        val rename = srcFile.renameTo(destFile)
        if (!rename) {
            copyFile(srcFile.absolutePath, destFile.absolutePath)
            deleteFileRecursive(srcFile)
        }
    }

    /**
     * copy file
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    fun copyFile(sourceFilePath: String?, destFilePath: String?): Boolean {
        val inputStream: InputStream
        try {
            inputStream = FileInputStream(sourceFilePath)
        } catch (e: FileNotFoundException) {
            throw RuntimeException("FileNotFoundException occurred. ", e)
        }
        return writeFile(destFilePath, inputStream)
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @param charsetName The name of a supported [&lt;/code&gt;charset&lt;code&gt;][java.nio.charset.Charset]
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    fun readFileToList(filePath: String?, charsetName: String?): List<String?>? {
        val file = File(filePath)
        val fileContent: MutableList<String?> = ArrayList()
        if (!file.isFile) {
            return null
        }

        var reader: BufferedReader? = null
        try {
            val `is` = InputStreamReader(FileInputStream(file), charsetName)
            reader = BufferedReader(`is`)
            var line: String? = null
            while ((reader.readLine().also { line = it }) != null) {
                fileContent.add(line)
            }
            return fileContent
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            close(reader!!)
        }
    }

    /**
     * get file name from path, not include suffix
     *
     * <pre>
     * getFileNameWithoutExtension(null)               =   null
     * getFileNameWithoutExtension("")                 =   ""
     * getFileNameWithoutExtension("   ")              =   "   "
     * getFileNameWithoutExtension("abc")              =   "abc"
     * getFileNameWithoutExtension("a.mp3")            =   "a"
     * getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     * getFileNameWithoutExtension("c:\\")              =   ""
     * getFileNameWithoutExtension("c:\\a")             =   "a"
     * getFileNameWithoutExtension("c:\\a.b")           =   "a"
     * getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     * getFileNameWithoutExtension("/home/admin")      =   "admin"
     * getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
    </pre> *
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    fun getFileNameWithoutExtension(filePath: String): String {
        if (StringUtil.isEmpty(filePath)) {
            return filePath
        }

        val extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR)
        val filePosi = filePath.lastIndexOf(File.separator)
        if (filePosi == -1) {
            return (if (extenPosi == -1) filePath else filePath.substring(0, extenPosi))
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1)
        }
        return (if (filePosi < extenPosi) filePath.substring(filePosi + 1, extenPosi) else filePath.substring(filePosi + 1))
    }

    /**
     * get file name from path, include suffix
     *
     * <pre>
     * getFileName(null)               =   null
     * getFileName("")                 =   ""
     * getFileName("   ")              =   "   "
     * getFileName("a.mp3")            =   "a.mp3"
     * getFileName("a.b.rmvb")         =   "a.b.rmvb"
     * getFileName("abc")              =   "abc"
     * getFileName("c:\\")              =   ""
     * getFileName("c:\\a")             =   "a"
     * getFileName("c:\\a.b")           =   "a.b"
     * getFileName("c:a.txt\\a")        =   "a"
     * getFileName("/home/admin")      =   "admin"
     * getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
    </pre> *
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    fun getFileName(filePath: String): String {
        if (StringUtil.isEmpty(filePath)) {
            return filePath
        }

        val filePosi = filePath.lastIndexOf(File.separator)
        return if ((filePosi == -1)) filePath else filePath.substring(filePosi + 1)
    }

    /**
     * get folder name from path
     *
     * <pre>
     * getFolderName(null)               =   null
     * getFolderName("")                 =   ""
     * getFolderName("   ")              =   ""
     * getFolderName("a.mp3")            =   ""
     * getFolderName("a.b.rmvb")         =   ""
     * getFolderName("abc")              =   ""
     * getFolderName("c:\\")              =   "c:"
     * getFolderName("c:\\a")             =   "c:"
     * getFolderName("c:\\a.b")           =   "c:"
     * getFolderName("c:a.txt\\a")        =   "c:a.txt"
     * getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     * getFolderName("/home/admin")      =   "/home"
     * getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
    </pre> *
     *
     * @param filePath
     * @return
     */
    fun getFolderName(filePath: String): String {
        if (StringUtil.isEmpty(filePath)) {
            return filePath
        }

        val filePosi = filePath.lastIndexOf(File.separator)
        return if ((filePosi == -1)) "" else filePath.substring(0, filePosi)
    }

    /**
     * get suffix of file from path
     *
     * <pre>
     * getFileExtension(null)               =   ""
     * getFileExtension("")                 =   ""
     * getFileExtension("   ")              =   "   "
     * getFileExtension("a.mp3")            =   "mp3"
     * getFileExtension("a.b.rmvb")         =   "rmvb"
     * getFileExtension("abc")              =   ""
     * getFileExtension("c:\\")              =   ""
     * getFileExtension("c:\\a")             =   ""
     * getFileExtension("c:\\a.b")           =   "b"
     * getFileExtension("c:a.txt\\a")        =   ""
     * getFileExtension("/home/admin")      =   ""
     * getFileExtension("/home/admin/a.txt/b")  =   ""
     * getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
    </pre> *
     *
     * @param filePath
     * @return
     */
    fun getFileExtension(filePath: String): String {
        if (StringUtil.isEmpty(filePath)) {
            return filePath
        }

        val extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR)
        val filePosi = filePath.lastIndexOf(File.separator)
        if (extenPosi == -1) {
            return ""
        }
        return if ((filePosi >= extenPosi)) "" else filePath.substring(extenPosi + 1)
    }

    fun makeDirs(filePath: String): Boolean {
        val folderName = getFolderName(filePath)
        if (StringUtil.isEmpty(folderName)) {
            return false
        }

        val folder = File(folderName)
        return if ((folder.exists() && folder.isDirectory)) true else folder.mkdirs()
    }

    /**
     * @param filePath
     * @return
     * @see .makeDirs
     */
    fun makeFolders(filePath: String): Boolean {
        return makeDirs(filePath)
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    fun isFileExist(filePath: String?): Boolean {
        if (StringUtil.isEmpty(filePath)) {
            return false
        }

        val file = File(filePath)
        return (file.exists() && file.isFile)
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    fun isFolderExist(directoryPath: String?): Boolean {
        if (StringUtil.isEmpty(directoryPath)) {
            return false
        }

        val dire = File(directoryPath)
        return (dire.exists() && dire.isDirectory)
    }

    fun deleteFileRecursive(file: File): Boolean {
        return deleteFileRecursive(file, null)
    }

    fun deleteFileRecursive(file: File, callback: Consumer<File?>?): Boolean {
        if (!file.exists()) {
            return true
        }

        if (file.isFile) {
            callback?.accept(file)
            return file.delete()
        }

        if (file.isDirectory) {
            val files = file.listFiles()
            if (!CollectionUtil.isEmpty(files)) {
                for (childFile in files) {
                    if (!deleteFileRecursive(childFile, callback)) {
                        return false
                    }
                }
            }
            callback?.accept(file)
            return file.delete()
        }

        return false
    }

    /**
     * get file size
     *
     *  * if path is null or empty, return -1
     *  * if path exist and it is a file, return file size, else return -1
     *
     *
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file does not exist.
     */
    fun getFileSize(path: String?): Long {
        if (StringUtil.isEmpty(path)) {
            return -1
        }

        val file = File(path)
        return (if (file.exists() && file.isFile) file.length() else -1)
    }

    fun close(vararg closeables: Closeable) {
        for (c in closeables) {
            try {
                c.close()
            } catch (ignored: IOException) {
            }
        }
    }


    private
    val DEFAULT_DIFF_PROVIDER: Function<Int, String> = Function { i: Int -> "($i)" }

    fun getNewFilePath(path: String, prefix: String?, names: Array<String?>?, suffix: String): String {
        return getNewFilePath(path, prefix, names, suffix, "_", DEFAULT_DIFF_PROVIDER)
    }

    fun getNewFilePath(path: String, prefix: String?, names: Array<String?>?, suffix: String, diffProvider: Function<Int, String>): String {
        return getNewFilePath(path, prefix, names, suffix, "_", diffProvider)
    }

    fun getNewFilePath(path: String, prefix: String?, names: Array<String?>?, suffix: String, sep: String, diffProvider: Function<Int, String>): String {
        val builder = StringBuilder()
        builder.append(prefix)
        if (names != null && names.size > 0) {
            builder.append(sep).append(StringUtil.toString(names, sep))
        }
        val name = builder.toString()

        var filePath = path + File.separator + name + suffix
        if (!isFileExist(filePath)) {
            return filePath
        }

        var i = 1
        do {
            filePath = path + File.separator + name + sep + diffProvider.apply(i) + suffix
            i++
        } while (isFileExist(filePath))

        return filePath
    }

    fun getResourceAsStream(path: String?): InputStream {
        return FileUtil::class.java.getResourceAsStream(path)
    }

    fun getAbsolutePath(relativePath: String?): String {
        val url: URL = FileUtil::class.java.getResource(relativePath)
        return url.path
    }

    fun getExternForm(path: String?): String {
        val resource: URL = FileUtil::class.java.getResource(path)
        return resource.toExternalForm()
    }

    fun createFileIfNotExist(path: String?) {
        if (isFileExist(path)) {
            return
        }
        val file = File(path)
        val parent = File(file.parent)
        if (!parent.exists()) {
            parent.mkdirs()
        }
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun extractZipFile(srcZipFile: File, targetDir: File, filter: Predicate<ZipEntry?>) {
        if (!srcZipFile.exists()) {
            return
        }
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        ZipFile(srcZipFile).use { zipFile ->
            val enumeration = zipFile.entries()
            while (enumeration.hasMoreElements()) {
                val zipEntry = enumeration.nextElement()
                if (!filter.test(zipEntry)) {
                    continue
                }
                val inputStream = zipFile.getInputStream(zipEntry)
                Files.copy(inputStream, targetDir.toPath().resolve(zipEntry.name), StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
}