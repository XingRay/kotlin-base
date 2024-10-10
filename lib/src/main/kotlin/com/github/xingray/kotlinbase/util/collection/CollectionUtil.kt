package com.github.xingray.kotlinbase.util.collection

import com.github.xingray.kotlinbase.container.interfaces.series.DoubleSeries
import com.github.xingray.kotlinbase.container.interfaces.series.IntSeries
import com.github.xingray.kotlinbase.container.interfaces.series.LongSeries
import com.github.xingray.kotlinbase.container.interfaces.series.Series
import com.github.xingray.kotlinbase.interfaces.consumer.index.ObjectIndexConsumer
import com.github.xingray.kotlinbase.interfaces.function.DoubleFunction
import com.github.xingray.kotlinbase.interfaces.function.IntFunction
import com.github.xingray.kotlinbase.interfaces.function.LongFunction
import com.github.xingray.kotlinbase.interfaces.function.index.DoubleIndexFunction
import com.github.xingray.kotlinbase.interfaces.function.index.IntIndexFunction
import com.github.xingray.kotlinbase.interfaces.function.index.LongIndexFunction
import com.github.xingray.kotlinbase.interfaces.function.index.ObjectIndexFunction
import com.github.xingray.kotlinbase.interfaces.predicate.*
import java.util.*
import java.util.function.BiConsumer
import java.util.function.BiPredicate
import java.util.function.Predicate
import kotlin.math.max
import kotlin.math.min

object CollectionUtil {

    private val EMPTY_INT_ARRAY: IntArray = IntArray(0)

    private val EMPTY_LONG_ARRAY: LongArray = LongArray(0)

    private val EMPTY_DOUBLE_ARRAY: DoubleArray = DoubleArray(0)

    fun <T> isEmpty(iterable: Iterable<T>?): Boolean {
        if (iterable == null) {
            return true
        }
        if (iterable is Collection) {
            return iterable.isEmpty()
        }
        return !iterable.iterator().hasNext()
    }

    fun <T> isEmpty(collection: java.util.Collection<T>?): Boolean {
        if (collection == null) {
            return true
        }
        return collection.size() == 0
    }

    fun <T> isEmpty(collection: java.util.List<T>?): Boolean {
        if (collection == null) {
            return true
        }
        return collection.size == 0
    }

    fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }

    fun <T> isEmpty(array: Array<T>?): Boolean {
        return array.isNullOrEmpty()
    }

    fun isEmpty(array: BooleanArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(array: ByteArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(array: CharArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(array: IntArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(array: LongArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(array: FloatArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(array: DoubleArray?): Boolean {
        return array == null || array.isEmpty()
    }

    fun isEmpty(series: Series<*>?): Boolean {
        return series == null || series.length() === 0
    }

    fun isEmpty(series: IntSeries?): Boolean {
        return series == null || series.length() === 0
    }

    fun isEmpty(series: LongSeries?): Boolean {
        return series == null || series.length() === 0
    }

    fun isEmpty(series: DoubleSeries?): Boolean {
        return series == null || series.length() === 0
    }

    fun hasElement(iterable: Iterable<*>?): Boolean {
        return iterable != null && iterable.iterator().hasNext()
    }

    fun hasElement(collection: Collection<*>?): Boolean {
        return collection != null && !collection.isEmpty()
    }

    fun hasElement(map: Map<*, *>?): Boolean {
        return map != null && !map.isEmpty()
    }

    fun <T> hasElement(array: Array<T>?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: BooleanArray?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: ByteArray?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: CharArray?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: IntArray?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: LongArray?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: FloatArray?): Boolean {
        return array != null && array.size > 0
    }

    fun hasElement(array: DoubleArray?): Boolean {
        return array != null && array.size > 0
    }

    fun <T> size(iterable: Iterable<T>?): Int {
        if (iterable == null) {
            return 0
        }
        if (iterable is Collection<*>) {
            return (iterable as Collection<T>).size
        }
        var size = 0
        for (e: T in iterable) {
            size++
        }
        return size
    }

    fun size(map: Map<*, *>?): Int {
        return if (map == null) 0 else map.size
    }

    fun <T> size(array: Array<T>?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: BooleanArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: ByteArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: CharArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: IntArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: LongArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: FloatArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(array: DoubleArray?): Int {
        return if (array == null) 0 else array.size
    }

    fun size(series: Series<*>?): Int {
        return if (series == null) 0 else series.length()
    }

    fun size(series: IntSeries?): Int {
        return if (series == null) 0 else series.length()
    }

    fun size(series: LongSeries?): Int {
        return if (series == null) 0 else series.length()
    }

    fun size(series: DoubleSeries?): Int {
        return if (series == null) 0 else series.length()
    }

    fun isValidIndex(iterable: Iterable<*>?, index: Int): Boolean {
        return index < size(iterable) && index >= 0
    }

    fun isValidIndex(collection: Collection<*>?, index: Int): Boolean {
        return index < size(collection) && index >= 0
    }

    fun isValidIndex(list: List<*>?, index: Int): Boolean {
        return index < size(list) && index >= 0
    }

    fun isValidIndex(array: BooleanArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun <T> isValidIndex(array: Array<T>?, index: Int): Boolean {
        return index < size<T>(array) && index >= 0
    }

    fun isValidIndex(array: ByteArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun isValidIndex(array: CharArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun isValidIndex(array: IntArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun isValidIndex(array: FloatArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun isValidIndex(array: DoubleArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun isValidIndex(array: LongArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun isOutOfIndex(iterable: Iterable<*>?, index: Int): Boolean {
        return index < 0 || index >= size(iterable)
    }

    fun <T> isOutOfIndex(array: Array<T>?, index: Int): Boolean {
        return index < 0 || index >= size<T>(array)
    }

    fun isOutOfIndex(array: BooleanArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun isOutOfIndex(array: ByteArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun isOutOfIndex(array: CharArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun isOutOfIndex(array: IntArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun isOutOfIndex(array: FloatArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun isOutOfIndex(array: DoubleArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun isOutOfIndex(array: LongArray?, index: Int): Boolean {
        return index < 0 || index >= size(array)
    }

    fun <T> hasElementByKey(map: Map<T, *>?, key: T): Boolean {
        return !(map == null || map.isEmpty()) && map.get(key) != null
    }

    fun <T> hasElementAt(iterable: Iterable<T>?, index: Int): Boolean {
        if (iterable == null) {
            return false
        }

        if (iterable is List<*>) {
            return (isValidIndex(iterable as List<T>?, index)
                    && (iterable as List<T?>).get(index) != null)
        }

        var i = 0
        for (t: T? in iterable) {
            if (i == index) {
                return t != null
            }
            i++
        }
        return false
    }

    fun <T> hasElementAt(array: Array<T?>, index: Int): Boolean {
        return isValidIndex(array, index) && array.get(index) != null
    }

    fun hasElementAt(array: BooleanArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun hasElementAt(array: ByteArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun hasElementAt(array: CharArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun hasElementAt(array: IntArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun hasElementAt(array: LongArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun hasElementAt(array: FloatArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    fun hasElementAt(array: DoubleArray?, index: Int): Boolean {
        return index < size(array) && index >= 0
    }

    inline fun <reified T> get(iterable: Iterable<T>?, index: Int): T? {
        if (iterable == null) {
            return null
        }
        if (iterable is Collection) {
            return get(iterable, index)
        }
        var i = 0
        for (t: T in iterable) {
            if (i == index) {
                return t
            }
            i++
        }
        return null
    }

    inline fun <reified T> get(collection: Collection<T>, index: Int): T? {
        return collection.elementAtOrNull(index)
    }

    fun <T> get(list: List<T>, index: Int): T? {
        var t: T? = null
        if (isValidIndex(list, index)) {
            t = list.get(index)
        }

        return t
    }

    fun <T> get(array: Array<T>, index: Int): T? {
        var t: T? = null
        if (isValidIndex(array, index)) {
            t = array.get(index)
        }

        return t
    }

    fun <K, V> get(map: Map<K, V>?, key: K): V? {
        return if (map == null) null else map.get(key)
    }

    fun <T> equals(t1: T?, t2: T): Boolean {
        if (t1 === t2) {
            return true
        }
        if (t1 == null) {
            return false
        }
        return (t1 == t2)
    }

    fun <T> equals(set1: Set<T>?, set2: Set<T>?): Boolean {
        if (set1 === set2) {
            return true
        }
        if (set1 == null || set2 == null) {
            return false
        }

        if (set1.size != set2.size) {
            return false
        }

        for (t: T? in set1) {
            if (t == null) {
                continue
            }

            if (!set2.contains(t)) {
                return false
            }
        }

        for (t: T? in set2) {
            if (t == null) {
                continue
            }

            if (!set1.contains(t)) {
                return false
            }
        }

        return true
    }

    fun <K, V> equals(map1: Map<K, V>?, map2: Map<K, V>?): Boolean {
        if (map1 === map2) {
            return true
        }
        if (map1 == null || map2 == null) {
            return false
        }
        val keySet1 = map1.keys
        val keySet2 = map2.keys
        if (!equals(keySet1, keySet2)) {
            return false
        }

        for (k: K in keySet1) {
            if (!equals(map1.get(k), map2.get(k))) {
                return false
            }
        }
        return true
    }

    fun <T> equals(list1: Iterable<T>?, list2: Iterable<T>?): Boolean {
        if (list1 === list2) {
            return true
        }
        if (list1 == null || list2 == null) {
            return false
        }

        if ((list1 is Collection<*> && list2 is Collection<*>
                    && ((list1 as Collection<*>).size != (list2 as Collection<*>).size))
        ) {
            return false
        }

        val iterator1 = list1.iterator()
        val iterator2 = list2.iterator()
        while (iterator1.hasNext() && iterator2.hasNext()) {
            val t1 = iterator1.next()
            val t2 = iterator2.next()
            if (!equals(t1, t2)) {
                return false
            }
        }
        return true
    }

    fun <T> equals(array1: Array<T>?, array2: Array<T>?): Boolean {
        if (array1 == array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (!equals(array1.get(i), array2.get(i))) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: BooleanArray?, array2: BooleanArray?): Boolean {
        if (array1 == array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: ByteArray?, array2: ByteArray?): Boolean {
        if (array1 == array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: CharArray?, array2: CharArray?): Boolean {
        if (array1 == array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: IntArray?, array2: IntArray?): Boolean {
        if (array1 == array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: LongArray?, array2: LongArray?): Boolean {
        if (array1 === array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: FloatArray?, array2: FloatArray?): Boolean {
        if (array1 === array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun equals(array1: DoubleArray?, array2: DoubleArray?): Boolean {
        if (array1 === array2) {
            return true
        }
        if (array1 == null || array2 == null) {
            return false
        }
        if (array1.size != array2.size) {
            return false
        }
        var i = 0
        val size = array1.size
        while (i < size) {
            if (array1.get(i) != array2.get(i)) {
                return false
            }
            i++
        }
        return true
    }

    fun <T> swap(list: MutableList<T>, i: Int, j: Int) {
        list.set(i, list.set(j, list.get(i)))
    }

    fun <T> swap(array: Array<T>, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: BooleanArray, i: Int, j: Int) {
        val tmp = array.get(i)
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: ByteArray, i: Int, j: Int) {
        val tmp = array.get(i)
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: CharArray, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: IntArray, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: LongArray, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: FloatArray, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    fun <T> swap(array: DoubleArray, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    fun indexOf(array: BooleanArray, target: Boolean): Int {
        return indexOf(array, target, 0)
    }

    fun indexOf(array: BooleanArray, target: Boolean, startIndex: Int): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (target == array.get(i)) {
                return i
            }
            i++
        }

        return -1
    }

    fun indexOf(array: ByteArray, target: Byte): Int {
        return indexOf(array, target, 0)
    }

    fun indexOf(array: ByteArray, target: Byte, startIndex: Int): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (target == array.get(i)) {
                return i
            }
            i++
        }

        return -1
    }

    fun indexOf(array: CharArray, target: Char): Int {
        return indexOf(array, target, 0)
    }

    fun indexOf(array: CharArray, target: Char, startIndex: Int): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (target == array.get(i)) {
                return i
            }
            i++
        }

        return -1
    }

    fun indexOf(array: IntArray?, target: Int): Int {
        return indexOf(array, target, 0)
    }

    fun indexOf(array: IntArray?, target: Int, startIndex: Int): Int {
        if (array == null) {
            return -1
        }
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (target == array.get(i)) {
                return i
            }
            i++
        }

        return -1
    }

    fun indexOf(array: FloatArray, target: Float): Int {
        return indexOf(array, target, 0)
    }

    fun indexOf(array: FloatArray, target: Float, startIndex: Int): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (target == array.get(i)) {
                return i
            }
            i++
        }

        return -1
    }

    fun indexOf(array: LongArray, target: Long): Int {
        return indexOf(array, target, 0)
    }

    fun indexOf(array: LongArray, target: Long, startIndex: Int): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (target == array.get(i)) {
                return i
            }
            i++
        }

        return -1
    }

    fun <T> indexOf(array: Array<T>, target: T): Int {
        return indexOf(array, target, 0)
    }

    fun <T> indexOf(array: Array<T>, target: T, startIndex: Int): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            if (equals(target, array.get(i))) {
                return i
            }
            i++
        }

        return -1
    }


    fun <T> indexOf(iterable: Iterable<T>?, target: T): Int {
        return indexOf(iterable, target, 0)
    }

    fun <T> indexOf(iterable: Iterable<T>?, target: T, startIndex: Int): Int {
        if (iterable == null) {
            return -1
        }

        if (iterable is List<*> && iterable is RandomAccess) {
            val list = iterable as List<T>
            var i = startIndex
            val size = list.size
            while (i < size) {
                if (equals(target, list.get(i))) {
                    return i
                }
                i++
            }
        } else {
            var index = -1
            for (t: T in iterable) {
                index++
                if (index < startIndex) {
                    continue
                }
                if (equals<T>(target, t)) {
                    return index
                }
            }
        }

        return -1
    }

    fun indexOf(array: BooleanArray, predicate: BooleanPredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: BooleanArray, startIndex: Int, predicate: BooleanPredicate): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun indexOf(array: ByteArray, predicate: BytePredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: ByteArray, startIndex: Int, predicate: BytePredicate): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun indexOf(array: CharArray, predicate: CharPredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: CharArray, startIndex: Int, matcher: CharPredicate): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (matcher.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun indexOf(array: IntArray?, predicate: IntPredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: IntArray?, startIndex: Int, predicate: IntPredicate): Int {
        if (array == null) {
            return -1
        }
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun indexOf(array: LongArray, predicate: LongPredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: LongArray, startIndex: Int, predicate: LongPredicate): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun indexOf(array: FloatArray, predicate: FloatPredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: FloatArray, startIndex: Int, predicate: FloatPredicate): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun indexOf(array: DoubleArray, predicate: DoublePredicate): Int {
        return indexOf(array, 0, predicate)
    }

    fun indexOf(array: DoubleArray, startIndex: Int, predicate: DoublePredicate): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun <T> indexOf(array: Array<T>, predicate: Predicate<T>): Int {
        return indexOf(array, 0, predicate)
    }

    fun <T> indexOf(array: Array<T>, startIndex: Int, predicate: Predicate<T>): Int {
        if (isOutOfIndex(array, startIndex)) {
            return -1
        }

        var i = 0
        val size = array.size
        while (i < size) {
            if (predicate.test(array.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun <T> indexOf(iterable: Iterable<T>, predicate: Predicate<T>): Int {
        return indexOf(iterable, 0, predicate)
    }

    fun <T> indexOf(iterable: Iterable<T>, startIndex: Int, predicate: Predicate<T>): Int {
        if (isOutOfIndex(iterable, startIndex)) {
            return -1
        }
        if (iterable is List<*> && iterable is RandomAccess) {
            val list = iterable as List<T>
            var i = startIndex
            val size = list.size
            while (i < size) {
                if (predicate.test(list.get(i))) {
                    return i
                }
                i++
            }
        } else {
            var index = -1
            for (t: T in iterable) {
                index++
                if (index < startIndex) {
                    continue
                }
                if (predicate.test(t)) {
                    return index
                }
            }
        }

        return -1
    }

    fun <T> find(array: Array<T>, predicate: Predicate<T>): T? {
        return find(array, 0, predicate)
    }

    fun <T> find(array: Array<T>, startIndex: Int, predicate: Predicate<T>): T? {
        if (isOutOfIndex(array, startIndex)) {
            return null
        }
        var i = startIndex
        val size = array.size
        while (i < size) {
            val t = array.get(i)
            if (predicate.test(t)) {
                return t
            }
            i++
        }
        return null
    }

    fun <T> find(iterable: Iterable<T>, predicate: Predicate<T>): T? {
        return find(iterable, 0, predicate)
    }

    fun <T> find(iterable: Iterable<T>, startIndex: Int, matcher: Predicate<T>): T? {
        if (isOutOfIndex(iterable, startIndex)) {
            return null
        }

        if (iterable is List<*> && iterable is RandomAccess) {
            val list = iterable as List<T>
            var i = startIndex
            val size = list.size
            while (i < size) {
                val t = list.get(i)
                if (matcher.test(t)) {
                    return t
                }
                i++
            }
        } else {
            var index = -1
            for (t: T in iterable) {
                index++
                if (index < startIndex) {
                    continue
                }
                if (matcher.test(t)) {
                    return t
                }
            }
        }

        return null
    }

    fun <T> find(array2: Array<Array<T>>, predicate: Predicate<T>): SearchResult<T>? {
        return find(array2, 0, 0, predicate)
    }

    fun <T> find(array2: Array<Array<T>>, startIndex0: Int, startIndex1: Int, predicate: Predicate<T>): SearchResult<T>? {
        if (isOutOfIndex(array2, startIndex0)) {
            return null
        }
        var index0 = startIndex0
        val size0 = array2.size
        while (index0 < size0) {
            val array = array2.get(index0)
            if (isOutOfIndex(array, startIndex1)) {
                index0++
                continue
            }
            var index1 = startIndex1
            val size1 = array.size
            while (index1 < size1) {
                val t = array.get(index1)
                if (predicate.test(t)) {
                    return SearchResult(t, index0, index1)
                }
                index1++
            }
            index0++
        }
        return null
    }

    fun <T> findAll(array: Array<T>, predicate: Predicate<T>): ArrayList<T> {
        return findAll(array, 0, predicate)
    }

    fun <T> findAll(array: Array<T>, startIndex: Int, predicate: Predicate<T>): ArrayList<T> {
        val result = ArrayList<T>()

        if (isOutOfIndex<T>(array, startIndex)) {
            return result
        }

        var i = startIndex
        val size = array.size
        while (i < size) {
            val t = array.get(i)
            if (predicate.test(t)) {
                result.add(t)
            }
            i++
        }

        return result
    }

    fun <T> findAll(iterable: Iterable<T>, predicate: Predicate<T>): ArrayList<T> {
        return findAll(iterable, 0, predicate)
    }

    fun <T> findAll(iterable: Iterable<T>, startIndex: Int, predicate: Predicate<T>): ArrayList<T> {
        val result = ArrayList<T>()

        if (isOutOfIndex(iterable, startIndex)) {
            return result
        }

        if (iterable is List<*> && iterable is RandomAccess) {
            val list = iterable as List<T>
            var i = startIndex
            val size = list.size
            while (i < size) {
                val t = list.get(i)
                if (predicate.test(t)) {
                    result.add(t)
                }
                i++
            }
        } else {
            var index = -1
            for (t: T in iterable) {
                index++
                if (index < startIndex) {
                    continue
                }
                if (predicate.test(t)) {
                    result.add(t)
                }
            }
        }

        return result
    }

    fun <T> filter(iterable: MutableIterable<T>, predicate: Predicate<T>) {
        if (isEmpty(iterable)) {
            return
        }
        val iterator = iterable.iterator()
        while (iterator.hasNext()) {
            val t = iterator.next()
            if (!predicate.test(t)) {
                iterator.remove()
            }
        }
    }

    fun <T> move(list: List<T?>?, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(list, fromIndex)) {
            return
        }

        if (isOutOfIndex(list, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            for (i in fromIndex downTo toIndex + 1) {
                Collections.swap(list, i, i - 1)
            }
        } else {
            for (i in fromIndex until toIndex) {
                Collections.swap(list, i, i + 1)
            }
        }
    }

    fun <T> move(array: Array<T>, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: BooleanArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: ByteArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: CharArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: IntArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: LongArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: FloatArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> move(array: DoubleArray, fromIndex: Int, toIndex: Int) {
        if (isOutOfIndex(array, fromIndex)) {
            return
        }

        if (isOutOfIndex(array, toIndex)) {
            return
        }

        if (fromIndex == toIndex) {
            return
        }

        if (fromIndex > toIndex) {
            val temp = array.get(fromIndex)
            System.arraycopy(array, toIndex, array, toIndex + 1, fromIndex - toIndex)
            array[toIndex] = temp
        } else {
            val temp = array.get(fromIndex)
            System.arraycopy(array, fromIndex + 1, array, fromIndex, toIndex - fromIndex)
            array[toIndex] = temp
        }
    }

    fun <T> distinct(iterable: MutableIterable<T>) {
        distinct(iterable, false)
    }

    fun <T> distinct(iterable: MutableIterable<T>, keepNull: Boolean) {
        if (isEmpty(iterable)) {
            return
        }
        val set = HashSet<T?>()

        val iterator = iterable.iterator()
        while (iterator.hasNext()) {
            val t: T? = iterator.next()
            if (t == null && !keepNull) {
                iterator.remove()
            }
            if (set.contains(t)) {
                iterator.remove()
                continue
            }
            set.add(t)
        }
    }

    fun <T, K> distinct(collection: MutableCollection<T>, mapper: java.util.function.Function<T, K>) {
        distinct(collection, false, mapper)
    }

    fun <T, K> distinct(collection: MutableCollection<T>, keepNull: Boolean, mapper: java.util.function.Function<T, K>) {
        if (isEmpty(collection as Collection<T>)) {
            return
        }
        val set = HashSet<K?>()

        val iterator = collection.iterator()
        while (iterator.hasNext()) {
            val t = iterator.next()
            val k: K? = mapper.apply(t)
            if (k == null && !keepNull) {
                iterator.remove()
            }
            if (set.contains(k)) {
                iterator.remove()
                continue
            }
            set.add(k)
        }
    }

    fun <T> newDistinctList(collection: Collection<T>, keepNull: Boolean = false): ArrayList<T?> {
        val list = ArrayList<T?>()
        if (isEmpty(collection)) {
            return list
        }

        val set = HashSet<T?>()

        for (t: T? in collection) {
            if (t == null && !keepNull) {
                continue
            }
            if (set.contains(t)) {
                continue
            }
            set.add(t)
            list.add(t)
        }

        return list
    }

    fun <T, K> newDistinctList(collection: Collection<T>, keepNull: Boolean = false, mapper: java.util.function.Function<T, K>): ArrayList<T> {
        val list = ArrayList<T>()
        if (isEmpty(collection)) {
            return list
        }

        val set = HashSet<K?>()

        for (t: T in collection) {
            val key: K? = mapper.apply(t)
            if (key == null && !keepNull) {
                continue
            }

            if (set.contains(key)) {
                continue
            }

            set.add(key)
            list.add(t)
        }
        set.clear()
        return list
    }

    fun <T> merge(vararg collections: Collection<T>?): ArrayList<T?> {
        return merge(false, *collections)
    }

    fun <T> merge(keepNull: Boolean, vararg collections: Collection<T>?): ArrayList<T?> {
        val list = ArrayList<T?>()
        if (isEmpty(collections)) {
            return list
        }
        val set = HashSet<T?>()

        for (c: Collection<T>? in collections) {
            if (c == null) {
                continue
            }
            for (t: T? in c) {
                if (t == null && !keepNull) {
                    continue
                }
                if (set.contains(t)) {
                    continue
                }
                list.add(t)
                set.add(t)
            }
        }
        return list
    }

    fun <T, K> merge(collections: Array<Collection<T>?>, keepNull: Boolean = false, mapper: java.util.function.Function<T, K>): ArrayList<T> {
        val list = ArrayList<T>()
        if (isEmpty(collections)) {
            return list
        }
        val set = HashSet<K?>()

        for (c: Collection<T>? in collections) {
            if (c == null) {
                continue
            }
            for (t: T in c) {
                val k: K? = mapper.apply(t)
                if (k == null && !keepNull) {
                    continue
                }
                if (set.contains(k)) {
                    continue
                }
                list.add(t)
                set.add(k)
            }
        }
        return list
    }

    fun <T> include(collection: Collection<T>, target: Collection<T>?): ArrayList<T> {
        val list = ArrayList<T>()
        if (isEmpty(collection)) {
            return list
        }
        if (isEmpty(target)) {
            return list
        }

        val set: HashSet<T>
        if (target is HashSet<*>) {
            set = target as HashSet<T>
        } else {
            set = HashSet(target)
        }

        for (t: T in collection) {
            if (set.contains(t)) {
                list.add(t)
            }
        }

        return list
    }

    fun <T, K> include(collection: Collection<T>, target: Collection<K>?, mapper: java.util.function.Function<T, K>): ArrayList<T> {
        val list = ArrayList<T>()
        if (isEmpty(collection)) {
            return list
        }
        if (isEmpty(target)) {
            return list
        }

        val set: HashSet<K>
        if (target is HashSet<*>) {
            set = target as HashSet<K>
        } else {
            set = HashSet(target)
        }

        for (t: T in collection) {
            val k = mapper.apply(t)
            if (set.contains(k)) {
                list.add(t)
            }
        }

        return list
    }

    fun <T> exclude(collection: Collection<T>, target: Collection<T>?): ArrayList<T> {
        val list = ArrayList<T>()
        if (isEmpty(collection)) {
            return list
        }
        if (isEmpty(target)) {
            list.addAll(collection)
            return list
        }

        val set: HashSet<T>
        if (target is HashSet<*>) {
            set = target as HashSet<T>
        } else {
            set = HashSet(target)
        }

        for (t: T in collection) {
            if (!set.contains(t)) {
                list.add(t)
            }
        }

        return list
    }

    fun <T, K> exclude(collection: Collection<T>, target: Collection<K>?, mapper: java.util.function.Function<T, K>): ArrayList<T> {
        val list = ArrayList<T>()
        if (isEmpty(collection)) {
            return list
        }
        if (isEmpty(target)) {
            list.addAll(collection)
            return list
        }

        val set: HashSet<K>
        if (target is HashSet<*>) {
            set = target as HashSet<K>
        } else {
            set = HashSet(target)
        }

        for (t: T in collection) {
            val k = mapper.apply(t)
            if (!set.contains(k)) {
                list.add(t)
            }
        }

        return list
    }

    fun <T> forEach(iterable: Iterable<T>, indexProcessor: ObjectIndexConsumer<T>) {
        if (isEmpty(iterable)) {
            return
        }

        var index = 0
        for (t: T in iterable) {
            indexProcessor.accept(t, index)
            index++
        }
    }

    fun <T> forEach(array: Array<T>, indexProcessor: ObjectIndexConsumer<T>) {
        if (isEmpty<T>(array)) {
            return
        }
        var index = 0
        for (t: T in array) {
            indexProcessor.accept(t, index)
            index++
        }
    }

    fun <T> reverseTraverse(iterable: Iterable<T>, indexProcessor: ObjectIndexConsumer<T>) {
        if (isEmpty(iterable)) {
            return
        }

        if (iterable is List) {
            reverseTraverse(iterable, indexProcessor)
        } else {
            reverseTraverse(toArray(iterable), indexProcessor)
        }
    }

    fun <T> reverseTraverse(array: Array<T>?, indexProcessor: ObjectIndexConsumer<T>) {
        if (isEmpty<T>(array)) {
            return
        }

        for (index in array!!.indices.reversed()) {
            indexProcessor.accept(array!!.get(index), index)
        }
    }

    fun <T> reverseTraverse(list: List<T>, indexProcessor: ObjectIndexConsumer<T>) {
        if (isEmpty(list)) {
            return
        }

        for (index in list.indices.reversed()) {
            indexProcessor.accept(list.get(index), index)
        }
    }

    fun <T, K> extractSet(collection: Collection<T>, keepNull: Boolean = false, mapper: java.util.function.Function<T, K>): HashSet<K?> {
        val set = HashSet<K?>()

        if (isEmpty(collection)) {
            return set
        }

        for (t: T in collection) {
            val key: K? = mapper.apply(t)
            if (key == null && !keepNull) {
                continue
            }

            set.add(key)
        }

        return set
    }

    fun <T, K> extractList(collection: Collection<T>, keepNull: Boolean = false, mapper: java.util.function.Function<T, K>): ArrayList<K?> {
        val list = ArrayList<K?>()

        if (isEmpty(collection)) {
            return list
        }

        for (t: T in collection) {
            val key: K? = mapper.apply(t)
            if (key == null && !keepNull) {
                continue
            }

            list.add(key)
        }

        return list
    }

    fun <T> getSizeRange(arrays: Array<Array<T>?>): com.github.xingray.kotlinbase.value.range.IntRange {
        var initialized = false
        var min = 0
        var max = 0


        for (array: Array<T>? in arrays) {
            val length = size<T>(array)
            if (initialized) {
                min = min(length.toDouble(), min.toDouble()).toInt()
                max = max(length.toDouble(), max.toDouble()).toInt()
            } else {
                min = length
                max = length
                initialized = true
            }
        }

        if (!initialized) {
            throw IllegalStateException("no array found")
        }

        return com.github.xingray.kotlinbase.value.range.IntRange(min, max)
    }

    fun <T> getMinSize(arrays: Array<Array<T>?>): Int {
        return getSizeRange<T>(arrays).start
    }

    fun <T> getMaxSize(arrays: Array<Array<T>?>): Int {
        return getSizeRange<T>(arrays).end
    }

    fun <T> clone(src: Iterable<T>?, mapper: java.util.function.Function<T, T>): ArrayList<T> {
        val cloneList = ArrayList<T>()

        if (src == null) {
            return cloneList
        }

        for (t: T in src) {
            val clone = mapper.apply(t)
            cloneList.add(clone)
        }

        return cloneList
    }


    // ================================================//
    fun getIntValuesRange(values: Iterable<IntArray?>): com.github.xingray.kotlinbase.value.range.IntRange? {
        var range: com.github.xingray.kotlinbase.value.range.IntRange? = null
        if (isEmpty(values)) {
            return null
        }

        for (valueArray: IntArray? in values) {
            if (valueArray == null || valueArray.size == 0) {
                continue
            }
            for (value: Int in valueArray) {
                if (range == null) {
                    range = com.github.xingray.kotlinbase.value.range.IntRange(value, value)
                } else {
                    if (value < range.start) {
                        range.start = value
                    }
                    if (value > range.end) {
                        range.end = value
                    }
                }
            }
        }
        return range
    }

    fun getLongValuesRange(values: Iterable<LongArray?>): com.github.xingray.kotlinbase.value.range.LongRange? {
        var range: com.github.xingray.kotlinbase.value.range.LongRange? = null
        if (isEmpty(values)) {
            return null
        }

        for (valueArray: LongArray? in values) {
            if (valueArray == null || valueArray.size == 0) {
                continue
            }
            for (value: Long in valueArray) {
                if (range == null) {
                    range = com.github.xingray.kotlinbase.value.range.LongRange(value, value)
                } else {
                    if (value < range.start) {
                        range.start = value
                    }
                    if (value > range.end) {
                        range.end = value
                    }
                }
            }
        }
        return range
    }

    fun getDoubleValuesRange(values: Iterable<DoubleArray?>): com.github.xingray.kotlinbase.value.range.DoubleRange? {
        var range: com.github.xingray.kotlinbase.value.range.DoubleRange? = null
        if (isEmpty(values)) {
            return null
        }

        for (valueArray: DoubleArray? in values) {
            if (valueArray == null || valueArray.size == 0) {
                continue
            }
            for (value: Double in valueArray) {
                if (java.lang.Double.isNaN(value)) {
                    continue
                }
                if (range == null) {
                    range = com.github.xingray.kotlinbase.value.range.DoubleRange(value, value)
                } else {
                    if (value < range.start) {
                        range.start = value
                    }
                    if (value > range.end) {
                        range.end = value
                    }
                }
            }
        }
        return range
    }


    // ================================================= //
    // ============================== //
    fun getRangeOfIntSeries(series: IntSeries?): com.github.xingray.kotlinbase.value.range.IntRange? {
        var range: com.github.xingray.kotlinbase.value.range.IntRange? = null

        if (series == null) {
            return null
        }
        val length: Int = series.length()
        if (length == 0) {
            return null
        }
        for (i in 0 until length) {
            val value: Int = series.get(i)
            if (range == null) {
                range = com.github.xingray.kotlinbase.value.range.IntRange(value, value)
            } else {
                if (value < range.start) {
                    range.start = value
                }
                if (value > range.end) {
                    range.end = value
                }
            }
        }
        return range
    }

    fun getRangeOfLongSeries(series: LongSeries?): com.github.xingray.kotlinbase.value.range.LongRange? {
        var range: com.github.xingray.kotlinbase.value.range.LongRange? = null

        if (series == null) {
            return null
        }
        val length: Int = series.length()
        if (length == 0) {
            return null
        }
        for (i in 0 until length) {
            val value: Long = series.get(i)
            if (range == null) {
                range = com.github.xingray.kotlinbase.value.range.LongRange(value, value)
            } else {
                if (value < range.start) {
                    range.start = value
                }
                if (value > range.end) {
                    range.end = value
                }
            }
        }
        return range
    }

    fun getRangeOfDoubleSeries(series: DoubleSeries?): com.github.xingray.kotlinbase.value.range.DoubleRange? {
        var range: com.github.xingray.kotlinbase.value.range.DoubleRange? = null

        if (series == null) {
            return null
        }
        val length: Int = series.length()
        if (length == 0) {
            return null
        }
        for (i in 0 until length) {
            val value: Double = series.get(i)
            if (java.lang.Double.isNaN(value)) {
                continue
            }
            if (range == null) {
                range = com.github.xingray.kotlinbase.value.range.DoubleRange(value, value)
            } else {
                if (value < range.start) {
                    range.start = value
                }
                if (value > range.end) {
                    range.end = value
                }
            }
        }
        return range
    }


    // =============================== //
    // =========================================//
    fun getRangeOfIntSeriesList(seriesList: Iterable<IntSeries?>): com.github.xingray.kotlinbase.value.range.IntRange? {
        var range: com.github.xingray.kotlinbase.value.range.IntRange? = null
        if (isEmpty(seriesList)) {
            return null
        }

        for (series: IntSeries? in seriesList) {
            if (series == null) {
                continue
            }
            val length: Int = series.length()
            if (length == 0) {
                continue
            }
            for (i in 0 until length) {
                val value: Int = series.get(i)
                if (range == null) {
                    range = com.github.xingray.kotlinbase.value.range.IntRange(value, value)
                } else {
                    if (value < range.start) {
                        range.start = value
                    }
                    if (value > range.end) {
                        range.end = value
                    }
                }
            }
        }
        return range
    }

    fun getRangeOfLongSeriesList(seriesList: Iterable<LongSeries?>): com.github.xingray.kotlinbase.value.range.LongRange? {
        var range: com.github.xingray.kotlinbase.value.range.LongRange? = null
        if (isEmpty(seriesList)) {
            return null
        }

        for (series: LongSeries? in seriesList) {
            if (series == null) {
                continue
            }
            val length: Int = series.length()
            if (length == 0) {
                continue
            }
            for (i in 0 until length) {
                val value: Long = series.get(i)
                if (range == null) {
                    range = com.github.xingray.kotlinbase.value.range.LongRange(value, value)
                } else {
                    if (value < range.start) {
                        range.start = value
                    }
                    if (value > range.end) {
                        range.end = value
                    }
                }
            }
        }
        return range
    }

    fun getRangeOfDoubleSeriesList(seriesList: Iterable<DoubleSeries?>): com.github.xingray.kotlinbase.value.range.DoubleRange? {
        var range: com.github.xingray.kotlinbase.value.range.DoubleRange? = null
        if (isEmpty(seriesList)) {
            return null
        }

        for (series: DoubleSeries? in seriesList) {
            if (series == null) {
                continue
            }
            val length: Int = series.length()
            if (length == 0) {
                continue
            }
            for (i in 0 until length) {
                val value: Double = series.get(i)
                if (range == null) {
                    range = com.github.xingray.kotlinbase.value.range.DoubleRange(value, value)
                } else {
                    if (value < range.start) {
                        range.start = value
                    }
                    if (value > range.end) {
                        range.end = value
                    }
                }
            }
        }
        return range
    }


    // =====================================================//
    fun compare(x: Boolean, y: Boolean): Int {
        return java.lang.Boolean.compare(x, y)
    }

    fun compare(x: Byte, y: Byte): Int {
        return java.lang.Byte.compare(x, y)
    }

    fun compare(x: Char, y: Char): Int {
        return Character.compare(x, y)
    }

    fun compare(x: Int, y: Int): Int {
        return Integer.compare(x, y)
    }

    fun compare(x: Long, y: Long): Int {
        return java.lang.Long.compare(x, y)
    }

    fun compare(x: Float, y: Float): Int {
        return java.lang.Float.compare(x, y)
    }

    fun compare(x: Double, y: Double): Int {
        return java.lang.Double.compare(x, y)
    }

    fun <K, V> getEntryList(map: Map<K, V>): List<Map.Entry<K, V>> {
        if (isEmpty(map)) {
            return emptyList()
        }

        val entrySet = map.entries
        if (isEmpty(entrySet)) {
            return emptyList()
        }
        return ArrayList(entrySet)
    }

    fun <T, E> convert(srcList: Iterable<T>, mapper: ObjectIndexFunction<T, E>): List<E> {
        if (isEmpty(srcList)) {
            return emptyList()
        }
        val dstList = ArrayList<E>()
        var index = 0
        for (t: T in srcList) {
            dstList.add(mapper.apply(t, index))
            index++
        }
        return dstList
    }

    fun <T, E> convert(array: Array<T>, mapper: ObjectIndexFunction<T, E>): List<E> {
        if (isEmpty<T>(array)) {
            return emptyList()
        }
        val dstList = ArrayList<E>()
        var index = 0
        for (t: T in array) {
            dstList.add(mapper.apply(t, index))
            index++
        }

        return dstList
    }

    fun <T> convert(iterable: Iterable<T>, mapper: IntIndexFunction<T>): IntArray {
        if (isEmpty(iterable)) {
            return EMPTY_INT_ARRAY
        }

        val size: Int = size(iterable)
        var index = 0
        val values = IntArray(size)
        for (t: T in iterable) {
            values[index] = mapper.apply(t, index)
            index++
        }

        return values
    }

    fun <T> convert(array: Array<T>, mapper: IntIndexFunction<T>): IntArray {
        if (isEmpty<T>(array)) {
            return EMPTY_INT_ARRAY
        }
        val size = array.size
        var index = 0
        val values = IntArray(size)
        for (t: T in array) {
            values[index] = mapper.apply(t, index)
            index++
        }

        return values
    }

    fun <T> convert(iterable: Iterable<T>, mapper: LongIndexFunction<T>): LongArray {
        if (isEmpty(iterable)) {
            return EMPTY_LONG_ARRAY
        }

        val size: Int = size(iterable)
        var index = 0
        val values = LongArray(size)
        for (t: T in iterable) {
            values[index] = mapper.apply(t, index)
            index++
        }

        return values
    }

    fun <T> convert(array: Array<T>, mapper: LongIndexFunction<T>): LongArray {
        if (isEmpty<T>(array)) {
            return EMPTY_LONG_ARRAY
        }
        val size = array.size
        var index = 0
        val values = LongArray(size)
        for (t: T in array) {
            values[index] = mapper.apply(t, index)
            index++
        }

        return values
    }

    fun <T> convert(iterable: Iterable<T>, mapper: DoubleIndexFunction<T>): DoubleArray {
        if (isEmpty(iterable)) {
            return EMPTY_DOUBLE_ARRAY
        }

        val size: Int = size(iterable)
        var index = 0
        val values = DoubleArray(size)
        for (t: T in iterable) {
            values[index] = mapper.apply(t, index)
            index++
        }

        return values
    }

    fun <T> convert(array: Array<T>, mapper: DoubleIndexFunction<T>): DoubleArray {
        if (isEmpty<T>(array)) {
            return EMPTY_DOUBLE_ARRAY
        }
        val size = array.size
        var index = 0
        val values = DoubleArray(size)
        for (t: T in array) {
            values[index] = mapper.apply(t, index)
            index++
        }

        return values
    }

    fun <T, E> convert(srcArray: Array<T>, dstArray: Array<E>, mapper: ObjectIndexFunction<T, E>) {
        if (isEmpty<T>(srcArray) || isEmpty<E>(dstArray)) {
            return
        }

        var index = 0
        val size = min(srcArray.size.toDouble(), dstArray.size.toDouble()).toInt()
        while (index < size) {
            val t = srcArray.get(index)
            dstArray[index] = mapper.apply(t, index)
            index++
        }
    }

    fun <T, E> convert(srcList: Iterable<T>, mapper: java.util.function.Function<T, E>): List<E> {
        if (isEmpty(srcList)) {
            return emptyList()
        }
        val dstList = ArrayList<E>()
        var i = 0
        for (t: T in srcList) {
            dstList.add(mapper.apply(t))
            i++
        }
        return dstList
    }

    fun <T, E> convert(array: Array<T>, mapper: java.util.function.Function<T, E>): List<E> {
        if (isEmpty<T>(array)) {
            return emptyList()
        }
        val dstList = ArrayList<E>()
        var i = 0
        for (t: T in array) {
            dstList.add(mapper.apply(t))
            i++
        }

        return dstList
    }

    fun <T> convert(iterable: Iterable<T>, mapper: IntFunction<T>): IntArray {
        if (isEmpty(iterable)) {
            return EMPTY_INT_ARRAY
        }

        val size: Int = size(iterable)
        var i = 0
        val values = IntArray(size)
        for (t: T in iterable) {
            values[i] = mapper.apply(t)
            i++
        }

        return values
    }

    fun <T> convert(array: Array<T>, mapper: IntFunction<T>): IntArray {
        if (isEmpty<T>(array)) {
            return EMPTY_INT_ARRAY
        }
        var i = 0
        val values = IntArray(array.size)
        for (t: T in array) {
            values[i] = mapper.apply(t)
            i++
        }

        return values
    }

    fun <T> convert(iterable: Iterable<T>, mapper: LongFunction<T>): LongArray {
        if (isEmpty(iterable)) {
            return EMPTY_LONG_ARRAY
        }

        val size: Int = size(iterable)
        var i = 0
        val values = LongArray(size)
        for (t: T in iterable) {
            values[i] = mapper.apply(t)
            i++
        }

        return values
    }

    fun <T> convert(array: Array<T>, mapper: LongFunction<T>): LongArray {
        if (isEmpty<T>(array)) {
            return EMPTY_LONG_ARRAY
        }
        val size = array.size
        var i = 0
        val values = LongArray(size)
        for (t: T in array) {
            values[i] = mapper.apply(t)
            i++
        }

        return values
    }

    fun <T> convert(iterable: Iterable<T>, mapper: DoubleFunction<T>): DoubleArray {
        if (isEmpty(iterable)) {
            return EMPTY_DOUBLE_ARRAY
        }

        val size: Int = size(iterable)
        var i = 0
        val values = DoubleArray(size)
        for (t: T in iterable) {
            values[i] = mapper.apply(t)
            i++
        }

        return values
    }

    fun <T> convert(array: Array<T>, mapper: DoubleFunction<T>): DoubleArray {
        if (isEmpty<T>(array)) {
            return EMPTY_DOUBLE_ARRAY
        }
        val size = array.size
        var i = 0
        val values = DoubleArray(size)
        for (t: T in array) {
            values[i] = mapper.apply(t)
            i++
        }

        return values
    }


    // =================================== //
    fun <T, E> convert(srcArray: Array<T>, dstArray: Array<E>, mapper: java.util.function.Function<T, E>) {
        if (isEmpty<T>(srcArray) || isEmpty<E>(dstArray)) {
            return
        }

        var i = 0
        val size = min(srcArray.size.toDouble(), dstArray.size.toDouble()).toInt()
        while (i < size) {
            val t = srcArray.get(i)
            dstArray[i] = mapper.apply(t)
            i++
        }
    }


    inline fun <reified T> concat(vararg arrays: Array<T?>): Array<T>? {
        var e: T? = null
        arrays.forEach { array ->
            e = array.find { it != null }
            if (e != null) {
                return@forEach
            }
        }
        if (e == null) {
            return null
        }

        // 使用 `reified` 来获取类型信息，并调用另一个 `concat` 函数
        return concat(T::class.java, *arrays.filterNotNull().map { it as Array<T> }.toTypedArray())
    }

    fun <T> concat(cls: Class<T>?, vararg arrays: Array<T>): Array<T> {
        val size: Int = arrays.size
        if (size == 0) {
            return java.lang.reflect.Array.newInstance(cls, 0) as Array<T>
        }


        var length = 0

        for (other: Array<T>? in arrays) {
            length += size<T>(other)
        }

        val result = java.lang.reflect.Array.newInstance(cls, length) as Array<T>
        var index = 0

        for (array: Array<T> in arrays) {
            if (isEmpty<T>(array)) {
                continue
            }
            System.arraycopy(array, 0, result, index, array.size)
            index += array.size
        }

        return result
    }

    fun <T> contains(container: Array<T>, elements: Array<T>): Boolean {
        if (isEmpty<T>(elements)) {
            return true
        }
        if (isEmpty<T>(container)) {
            return false
        }
        var i = 0
        val elementSize = size<T>(elements)
        while (i < elementSize) {
            val element = elements.get(i)
            if (!contains(container, element)) {
                return false
            }
            i++
        }
        return true
    }

    fun <T> contains(container: Array<T>, element: T?): Boolean {
        if (isEmpty<T>(container)) {
            return false
        }
        var i = 0
        val size = size<T>(container)
        while (i < size) {
            val t: T = container.get(i) ?: if (element == null) {
                return true
            } else {
                i++
                continue
            }
            if ((t == element)) {
                return true
            }
            i++
        }

        return false
    }

    fun contains(container: IntArray, element: Int): Boolean {
        if (isEmpty(container)) {
            return false
        }

        var i = 0
        val size: Int = size(container)
        while (i < size) {
            val t = container.get(i)
            if (t == element) {
                return true
            }
            i++
        }

        return false
    }

    fun <T> listOf(vararg array: T): List<T> {
        if (array.isEmpty()) {
            return ArrayList()
        }

        return Arrays.asList(*array)
    }

    fun <T> arrayOf(vararg array: T): Array<T> {
        return array as Array<T>
    }

    fun <T> toList(array: Array<T>?): List<T> {
        return if (array == null) ArrayList() else Arrays.asList(*array)
    }

    fun <T> toArray(iterable: Iterable<T>): Array<T>? {
        val t = iterable.find { it != null }
        if (t == null) {
            return null
        }

        val javaClass = t.javaClass
        return toArray(iterable, javaClass as Class<T>)
    }

    fun <T> toArray(iterable: Iterable<T>, cls: Class<T>?): Array<T> {
        val size = size<T>(iterable)
        val array = java.lang.reflect.Array.newInstance(cls, size) as Array<T>
        if (size == 0) {
            return array
        }

        if (iterable is java.util.List<*>) {
            return (iterable as java.util.List<T>).toArray(array)
        }

        var index = -1
        for (t: T in iterable) {
            index++
            array[index] = t
        }

        return array
    }

    fun <T, R> toArray(iterable: Iterable<T>, cls: Class<R>?, mapper: java.util.function.Function<T, R>): Array<R> {
        val size = size<T>(iterable)
        val array = java.lang.reflect.Array.newInstance(cls, size) as Array<R>
        return toArray<T, R>(iterable, size, array, mapper)
    }

    fun <T, R> toArray(iterable: Iterable<T>, array: Array<R>, mapper: java.util.function.Function<T, R>): Array<R> {
        val size = size<T>(iterable)
        val arraySize = size<R>(array)
        return toArray<T, R>(iterable, size, array, mapper)
    }

    private fun <T, R> toArray(iterable: Iterable<T>, size: Int, array: Array<R>, mapper: java.util.function.Function<T, R>): Array<R> {
        if (size == 0) {
            return array
        }
        var index = 0
        for (t: T in iterable) {
            array[index] = mapper.apply(t)
            ++index
        }

        return array
    }

    fun <T, R> toArray(iterable: Iterable<T>, cls: Class<R>?, mapper: ObjectIndexFunction<T, R>): Array<R> {
        val size = size<T>(iterable)
        val array = java.lang.reflect.Array.newInstance(cls, size) as Array<R>
        return toArray(iterable, size, array, mapper)
    }

    fun <T, R> toArray(iterable: Iterable<T>, array: Array<R>, mapper: ObjectIndexFunction<T, R>): Array<R> {
        val size = size<T>(iterable)
        val arraySize = size<R>(array)
        return toArray(iterable, size, array, mapper)
    }

    private fun <T, R> toArray(iterable: Iterable<T>, size: Int, array: Array<R>, mapper: ObjectIndexFunction<T, R>): Array<R> {
        if (size == 0) {
            return array
        }
        var index = 0
        for (t: T in iterable) {
            array[index] = mapper.apply(t, index)
            ++index
        }

        return array
    }

    fun splitToRanges(from: Int, to: Int, rangeSize: Int): Array<IntRange?> {
        if (rangeSize <= 0) {
            throw IllegalArgumentException()
        }
        if (from == to) {
            return kotlin.arrayOf(IntRange(from, to))
        } else if (from < to) {
            val rangeCount = getRangeCount(from, to, rangeSize)
            val ranges = arrayOfNulls<IntRange>(rangeCount)
            for (i in 0 until rangeCount) {
                ranges[i] = IntRange(from + i * rangeSize, min((from + (i + 1) * rangeSize - 1).toDouble(), to.toDouble()).toInt())
            }
            return ranges
        } else {
            val rangeCount = getRangeCount(from, to, rangeSize)
            val ranges = arrayOfNulls<IntRange>(rangeCount)
            for (i in 0 until rangeCount) {
                ranges[i] = IntRange(from - i * rangeSize, max((from - (i + 1) * rangeSize + 1).toDouble(), to.toDouble()).toInt())
            }
            return ranges
        }
    }

    fun getRangeCount(from: Int, to: Int, rangeSize: Int): Int {
        if (rangeSize <= 0) {
            throw IllegalArgumentException()
        }
        if (from == to) {
            return 1
        }

        val range = (Math.abs((to - from).toDouble()) + 1).toInt()
        var rangeCount = range / rangeSize
        if (range % rangeSize != 0) {
            rangeCount++
        }
        return rangeCount
    }

    fun getRange(form: Int, to: Int, rangeSize: Int, rangeIndex: Int): IntRange? {
        return splitToRanges(form, to, rangeSize).get(rangeIndex)
    }

    fun <T> newReversed(source: Iterable<T>): List<T?> {
        if (isEmpty(source)) {
            return emptyList<T>()
        }

        val target: List<T?>
        if (source is Collection<*>) {
            val c = source as Collection<T?>
            target = ArrayList(c)
            if (target.size == 1) {
                return target
            }
            Collections.reverse(target)
        } else {
            val size = size<T>(source)
            val array = arrayOfNulls<Any>(size)

            var index = 0
            for (t: T in source) {
                array[size - 1 - index] = t
                index++
            }

            target = ArrayList(Arrays.asList(*array)) as List<T?>
        }

        return target
    }

    fun <E> remove(list: MutableList<E>, index: Int, range: Int): Int {
        if (list.isEmpty()) {
            return 0
        }

        if (list is RandomAccess) {
            var removedCount = 0
            val lastIndex = index + range - 1
            for (i in 0 until range) {
                list.removeAt(lastIndex - i)
                removedCount++
            }
            return removedCount
        } else {
            return remove(list as MutableIterable<E>, index, range)
        }
    }

    fun <E> remove(iterable: MutableIterable<E>, index: Int, range: Int): Int {
        if (isEmpty(iterable)) {
            return 0
        }

        var removedCount = 0


        var i = 0
        val iterator = iterable.iterator()
        while (i < index && iterator.hasNext()) {
            iterator.next()
            i++
        }

        if (i < index) {
            return removedCount
        }

        i = 0
        while (i < range && iterator.hasNext()) {
            iterator.next()
            iterator.remove()
            removedCount++
            i++
        }

        return removedCount
    }

    fun <T> remove(iterable: MutableIterable<T>, matcher: Predicate<T>): Int {
        return remove(iterable, 0, matcher)
    }

    fun <T> remove(iterable: MutableIterable<T>, startIndex: Int, matcher: Predicate<T>): Int {
        if (isOutOfIndex(iterable, startIndex)) {
            return 0
        }

        var removeCount = 0
        var index = 0
        val iterator = iterable.iterator()
        while (iterator.hasNext()) {
            val t = iterator.next()
            if (index >= startIndex && matcher.test(t)) {
                iterator.remove()
                removeCount++
            }
            ++index
        }
        return removeCount
    }

    fun <T> intMinMax(values: IntArray?): com.github.xingray.kotlinbase.value.range.IntRange? {
        if (values == null) {
            return null
        }

        val intRange = com.github.xingray.kotlinbase.value.range.IntRange(values.get(0), values.get(0))

        var i = 1
        val length = values.size
        while (i < length) {
            val value = values.get(i)
            if (value < intRange.start) {
                intRange.start = value
            }
            if (value > intRange.end) {
                intRange.end = value
            }
            i++
        }

        return intRange
    }

    fun <T> longMinMax(values: LongArray?): com.github.xingray.kotlinbase.value.range.LongRange? {
        if (values == null) {
            return null
        }

        val longRange = com.github.xingray.kotlinbase.value.range.LongRange(values.get(0), values.get(0))

        var i = 1
        val length = values.size
        while (i < length) {
            val value = values.get(i)
            if (value < longRange.start) {
                longRange.start = value
            }
            if (value > longRange.end) {
                longRange.end = value
            }
            i++
        }

        return longRange
    }

    fun <T> doubleMinMax(values: DoubleArray?): com.github.xingray.kotlinbase.value.range.DoubleRange? {
        if (values == null) {
            return null
        }

        val doubleRange = com.github.xingray.kotlinbase.value.range.DoubleRange(values.get(0), values.get(0))

        var i = 1
        val length = values.size
        while (i < length) {
            val value = values.get(i)
            if (value < doubleRange.start) {
                doubleRange.start = value
            }
            if (value > doubleRange.end) {
                doubleRange.end = value
            }
            i++
        }

        return doubleRange
    }


    // ================================= //
    fun <T> intMinMax(iterable: Iterable<T>?, mapper: IntFunction<T>): com.github.xingray.kotlinbase.value.range.IntRange? {
        if (iterable == null) {
            return null
        }
        var intRange: com.github.xingray.kotlinbase.value.range.IntRange? = null
        var index = 0
        for (t: T in iterable) {
            val value: Int = mapper.apply(t)
            if (index == 0) {
                intRange = com.github.xingray.kotlinbase.value.range.IntRange(value, value)
            } else {
                if (intRange == null) {
                    break
                }
                if (value < intRange.start) {
                    intRange.start = value
                }
                if (value > intRange.end) {
                    intRange.end = value
                }
            }

            index++
        }

        return intRange
    }

    fun <T> longMinMax(iterable: Iterable<T>?, mapper: LongFunction<T>): com.github.xingray.kotlinbase.value.range.LongRange? {
        if (iterable == null) {
            return null
        }
        var intRange: com.github.xingray.kotlinbase.value.range.LongRange? = null
        var index = 0
        for (t: T in iterable) {
            val value: Long = mapper.apply(t)
            if (index == 0) {
                intRange = com.github.xingray.kotlinbase.value.range.LongRange(value, value)
            } else {
                if (intRange == null) {
                    break
                }
                if (value < intRange.start) {
                    intRange.start = value
                }
                if (value > intRange.end) {
                    intRange.end = value
                }
            }

            index++
        }

        return intRange
    }

    fun <T> doubleMinMax(iterable: Iterable<T>?, mapper: DoubleFunction<T>): com.github.xingray.kotlinbase.value.range.DoubleRange? {
        if (iterable == null) {
            return null
        }
        var doubleRange: com.github.xingray.kotlinbase.value.range.DoubleRange? = null
        var index = 0
        for (t: T in iterable) {
            val value: Double = mapper.apply(t)
            if (index == 0) {
                doubleRange = com.github.xingray.kotlinbase.value.range.DoubleRange(value, value)
            } else {
                if (doubleRange == null) {
                    break
                }
                if (value < doubleRange.start) {
                    doubleRange.start = value
                }
                if (value > doubleRange.end) {
                    doubleRange.end = value
                }
            }

            index++
        }

        return doubleRange
    }


    // ============================ //
    fun <T> intMinMax(iterable: Iterable<T>?, minMapper: IntFunction<T>, maxMapper: IntFunction<T>): com.github.xingray.kotlinbase.value.range.IntRange? {
        if (iterable == null) {
            return null
        }
        var intRange: com.github.xingray.kotlinbase.value.range.IntRange? = null
        var index = 0
        for (t: T in iterable) {
            val min: Int = minMapper.apply(t)
            val max: Int = maxMapper.apply(t)

            if (index == 0) {
                intRange = com.github.xingray.kotlinbase.value.range.IntRange(min, max)
            } else {
                if (intRange == null) {
                    break
                }
                if (min < intRange.start) {
                    intRange.start = min
                }
                if (max > intRange.end) {
                    intRange.end = max
                }
            }

            index++
        }

        return intRange
    }

    fun <T> longMinMax(iterable: Iterable<T>?, minMapper: LongFunction<T>, maxMapper: LongFunction<T>): com.github.xingray.kotlinbase.value.range.LongRange? {
        if (iterable == null) {
            return null
        }
        var intRange: com.github.xingray.kotlinbase.value.range.LongRange? = null
        var index = 0
        for (t: T in iterable) {
            val min: Long = minMapper.apply(t)
            val max: Long = maxMapper.apply(t)

            if (index == 0) {
                intRange = com.github.xingray.kotlinbase.value.range.LongRange(min, max)
            } else {
                if (intRange == null) {
                    break
                }
                if (min < intRange.start) {
                    intRange.start = min
                }
                if (max > intRange.end) {
                    intRange.end = max
                }
            }

            index++
        }

        return intRange
    }

    fun <T> doubleMinMax(iterable: Iterable<T>?, minMapper: DoubleFunction<T>, maxMapper: DoubleFunction<T>): com.github.xingray.kotlinbase.value.range.DoubleRange? {
        if (iterable == null) {
            return null
        }
        var doubleRange: com.github.xingray.kotlinbase.value.range.DoubleRange? = null
        var index = 0
        for (t: T in iterable) {
            val min: Double = minMapper.apply(t)
            val max: Double = maxMapper.apply(t)

            if (index == 0) {
                doubleRange = com.github.xingray.kotlinbase.value.range.DoubleRange(min, max)
            } else {
                if (doubleRange == null) {
                    break
                }
                if (min < doubleRange.start) {
                    doubleRange.start = min
                }
                if (max > doubleRange.end) {
                    doubleRange.end = max
                }
            }

            index++
        }

        return doubleRange
    }


    /**
     * 将逻辑上具有树形结构关系的list组装为树形list
     *
     * @param all        原本只是逻辑上具有树形结构关系，但是数据上只是普通列表的list对象
     * @param root       树形结构的根节点，本身不会添加到返回结果中，可以根据条件自行创建
     * @param predicate  判断两个对象是否是逻辑上的父子节点，test(T t, U u); t是子节点，u是父节点就返回 true
     * @param comparator 节点排序，null则不排序
     * @param consumer   将children添加到 node 中，根据具体node的类型自行实现
     * @param <T>        node的数据类型
     * @return 树形结构的list
    </T> */
    fun <T> listAsTree(all: MutableList<T>, root: T, predicate: BiPredicate<T, T>, comparator: Comparator<T>?, consumer: BiConsumer<T, List<T>?>): List<T> {
        val tree = filterAndDelete(all, root, predicate, comparator)
        val queue: Queue<T> = LinkedList(tree)
        while (!queue.isEmpty()) {
            val entity = queue.poll()
            val children = filterAndDelete(all, entity, predicate, comparator)
            consumer.accept(entity, children)
            queue.addAll(children)
        }
        return tree
    }

    private fun <T> filterAndDelete(all: MutableIterable<T>, node: T, predicate: BiPredicate<T, T>, comparator: Comparator<T>?): List<T> {
        var children: MutableList<T>? = null
        val iterator = all.iterator()
        while (iterator.hasNext()) {
            val t = iterator.next()
            if (predicate.test(t, node)) {
                if (children == null) {
                    children = ArrayList()
                }
                children.add(t)
                iterator.remove()
            }
        }
        if (children == null) {
            return emptyList()
        } else {
            if (comparator != null) {
                children.sortWith(comparator)
            }
            return children
        }
    }

    fun <T> filterAndDelete(all: MutableIterable<T>, predicate: Predicate<T>): List<T>? {
        var children: MutableList<T>? = null
        val iterator = all.iterator()
        while (iterator.hasNext()) {
            val t = iterator.next()
            if (predicate.test(t)) {
                if (children == null) {
                    children = ArrayList()
                }
                children.add(t)
                iterator.remove()
            }
        }
        return Objects.requireNonNullElse(children, emptyList())
    }
}