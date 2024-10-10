package com.github.xingray.kotlinbase.util

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.Volatile
import kotlin.math.max
import kotlin.math.min

object TaskExecutor {

    private val CPU_COUNT: Int = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE: Int = max(4.0, min((CPU_COUNT - 1).toDouble(), 8.0)).toInt()
    private val MAXIMUM_POOL_SIZE: Int = CPU_COUNT * 2 + 1

    /**
     * IO读写线程池,最多CORE_POOL_SIZE同时执行
     */
    @Volatile
    private var sIOPool: ExecutorService? = null

    /**
     * 全局cachePool,适用于AsyncHttpClient等不
     * 限制任务数的请求
     */
    @Volatile
    private var sCachePool: ExecutorService? = null

    /**
     * 串行线程池
     */
    @Volatile
    private var sSerialPool: ExecutorService? = null

    /**
     * 繁重 任务线程池，适用于像ImageLoader转换图像这种时间不长但又很占CPU的任务
     * 排队执行的[ThreadPoolExecutor],核心线程为CORE_POOL_SIZE+1个
     */
    @Volatile
    private var sCpuPool: ExecutorService? = null

    /**
     * 主线程[Executor]对象
     */
    @Volatile
    private var sUIExecutor: Executor? = null

    private fun TaskExecutor() {
        throw UnsupportedOperationException()
    }

    fun ui(task: Runnable?) {
        uiPool().execute(task)
    }

    fun io(task: Runnable?) {
        ioPool()!!.execute(task)
    }

    fun cpu(task: Runnable?) {
        cpuPool()!!.execute(task)
    }

    fun enqueue(task: Runnable?) {
        serialPool()!!.execute(task)
    }

    fun infinite(task: Runnable?) {
        cachePool()!!.execute(task)
    }

    fun uiPool(): Executor {
        val executor = sUIExecutor ?: let {
            throw NullPointerException("call setUiPoolExecutor to set ui poll executor first !")
        }
        return executor
    }

    fun setUiPoolExecutor(executor: Executor?) {
        sUIExecutor = executor
    }

    fun ioPool(): Executor? {
        if (sIOPool == null) {
            synchronized(TaskExecutor::class.java) {
                if (sIOPool == null) {
                    sIOPool = ThreadPoolExecutor(
                        CORE_POOL_SIZE,
                        15,
                        5L, TimeUnit.SECONDS,
                        LinkedBlockingQueue(),
                        NamedThreadFactory("io-pool")
                    )
                }
            }
        }
        return sIOPool
    }

    private fun cpuPool(): Executor? {
        if (sCpuPool == null) {
            synchronized(TaskExecutor::class.java) {
                if (sCpuPool == null) {
                    sCpuPool = ThreadPoolExecutor(
                        1,
                        1,
                        5L, TimeUnit.SECONDS,
                        LinkedBlockingQueue(128),
                        NamedThreadFactory("cpu-pool")
                    )
                }
            }
        }
        return sCpuPool
    }

    private fun serialPool(): Executor? {
        if (sSerialPool == null) {
            synchronized(TaskExecutor::class.java) {
                if (sSerialPool == null) {
                    sSerialPool = ThreadPoolExecutor(
                        1,
                        1,
                        0L, TimeUnit.MILLISECONDS,
                        LinkedBlockingQueue(),
                        NamedThreadFactory("serial-pool")
                    )
                }
            }
        }
        return sSerialPool
    }

    private fun cachePool(): Executor? {
        if (sCachePool == null) {
            synchronized(TaskExecutor::class.java) {
                if (sCachePool == null) {
                    sCachePool = ThreadPoolExecutor(
                        0,
                        MAXIMUM_POOL_SIZE,
                        60L, TimeUnit.SECONDS,
                        SynchronousQueue(),
                        NamedThreadFactory("cache-pool")
                    )
                }
            }
        }
        return sCachePool
    }

    private class NamedThreadFactory internal constructor(private val mThreadName: String) : ThreadFactory {
        private val mCount = AtomicInteger(1)

        override fun newThread(r: Runnable): Thread {
            return Thread(r, mThreadName + "#" + mCount.getAndIncrement())
        }
    }
}