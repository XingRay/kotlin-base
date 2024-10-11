package com.github.xingray.kotlinbase.progresstask


class ProgressTask<T> {

    companion object {
        @JvmStatic
        private val TAG = ProgressTask::class.java.simpleName
    }

    private var mProgressCallback: ((progress: Int, total: Int, result: T) -> Unit)? = null
    private var mCanceled = false
    private var mTasks = mutableListOf<() -> T>()

    fun start() {

        val total = mTasks.size

        for (i in 0..<total) {
            if (mCanceled) {
                break
            }
            val result = mTasks[i].invoke()
            mProgressCallback?.invoke(i + 1, total, result)
        }
    }

    fun cancel() {
        mCanceled = true
    }

    fun setProgressCallback(progressCallback: (progress: Int, total: Int, T) -> Unit) {
        mProgressCallback = progressCallback
    }

    fun setTasks(tasks: List<() -> T>) {
        mTasks.addAll(tasks)
    }
}