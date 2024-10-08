package com.nanosecond.makeup.kotlin.pool

import java.util.LinkedList
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ObjectPool<T>(
    private val create: () -> T,
    private val reset: (T) -> Unit,
    private val maxPoolSize: Int
) {
    private val pool = LinkedList<T>()
    private val lock = ReentrantLock()

    // 借用对象
    fun borrow(): T {
        lock.withLock {
            return if (pool.isEmpty()) {
                create()
            } else {
                pool.poll()
            }
        }
    }

    // 归还对象
    fun returnBack(obj: T) {
        lock.withLock {
            if (pool.size < maxPoolSize) {
                reset(obj)
                pool.offer(obj)
            }
        }
    }

    fun clear() {
        lock.withLock {
            pool.clear()
        }
    }
}
