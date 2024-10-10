package com.github.xingray.kotlinbase.container.container

import com.github.xingray.kotlinbase.container.interfaces.container.Container
import java.util.function.BiFunction
import java.util.function.Predicate

/**
 * todo
 */
class IteratableContainer<T>(iterable: Iterable<T>) : Container<Int, T> {
    private val iterable: Iterable<T>

    init {
        this.iterable = iterable
    }

    override fun hasElement(): Boolean {
        return iterable.iterator().hasNext()
    }

    override val isEmpty: Boolean
        get() = !iterable.iterator().hasNext()

    override fun get(index: Int): T? {
//        return iterable.get(index);
        return null
    }

    override fun set(index: Int, t: T) {
//        iterable.set(index, t);
    }

    override fun size(): Int {
//        return iterable.size();
        return 0
    }

    override fun toList(): List<T> {
        //        return new ArrayList<>(iterable);
        return emptyList()
    }

    override fun toArray(): Array<T> {
        //        T t = find(Objects::nonNull);
//        if (t == null) {
//            return null;
//        }
//
//        int size = iterable.size();
//        // noinspection unchecked
//        T[] array = (T[]) java.lang.reflect.Array.newInstance(t.getClass(), size);
//        return iterable.toArray(array);
        return arrayOfNulls<Any>(0) as Array<T>
    }

    override fun toMap(): Map<Int, T> {
        //        TreeMap<Integer, T> map = new TreeMap<>(Comparator.naturalOrder());
//        for (int i = 0; i < iterable.size(); i++) {
//            map.put(i, iterable.get(i));
//        }
//        return map;
        return mapOf()
    }

    override fun toSet(): Set<T> {
        //        return Set.copyOf(iterable);
        return setOf()
    }

    override fun find(predicate: Predicate<T>): T? {
        for (t in iterable) {
            if (predicate.test(t)) {
                return t
            }
        }
        return null
    }

    override fun findAll(predicate: Predicate<T>): Container<Int, T> {
        var result: MutableList<T>? = null
        for (t in iterable) {
            if (predicate.test(t)) {
                if (result == null) {
                    result = ArrayList()
                }
                result.add(t)
            }
        }
        if (result == null) {
            return EmptyContainer.getInstance()
        }
        return IteratableContainer(result)
    }

    override fun merge(container: Container<Int, T>, biConsumer: BiFunction<T, T, T>): Container<Int, T> {
//        if (container.isEmpty()) {
//            return new IteratableContainer<>(iterable);
//        }
//        List<T> target = container.toList();
//
//        List<T> merged = new ArrayList<>(iterable.size() + target.size());
//        merged.addAll(iterable);
//        merged.addAll(target);
//        return new IteratableContainer<>(merged);
        return Containers.empty()
    }

    override fun copy(): Container<Int, T> {
        return IteratableContainer(iterable)
    }

    override fun toString(): String {
        return "ListContainer{" +
                "list=" + iterable +
                '}'
    }
}
