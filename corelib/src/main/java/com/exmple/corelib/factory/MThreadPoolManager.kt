package com.exmple.corelib.factory

import java.util.concurrent.*

object MThreadPoolManager {
    val mNormalPool = ThreadPoolProxy(3, 3, (5 * 1000).toLong())
    class ThreadPoolProxy(private val mCorePoolSize: Int, private val mMaximumPoolSize: Int, private val mKeepAliveTime: Long) {
        private var mPool: ThreadPoolExecutor? = null  // 代理对象内部保存的是原来类的对象
        private fun initPool() {
            if (mPool == null || mPool?.isShutdown!!) {
                val unit = TimeUnit.MILLISECONDS//单位
                var workQueue: BlockingQueue<Runnable>? = null//阻塞队列
                workQueue = ArrayBlockingQueue(5)//FIFO,大小有限制，为3个
                val threadFactory = Executors.defaultThreadFactory()//线程工厂
                val handler = ThreadPoolExecutor.DiscardPolicy()//不做任何处理
                // 创建线程池
                mPool = ThreadPoolExecutor(mCorePoolSize,
                        mMaximumPoolSize,
                        mKeepAliveTime,
                        unit,
                        workQueue,
                        threadFactory,
                        handler)
            }
        }

        /**
         * 执行任务
         */
        fun execute(task: Runnable) {
            initPool()
            mPool?.execute(task)
        }

        // 提交任务
        fun submit(task: Runnable): Future<*>? {
            initPool()
            return mPool?.submit(task)
        }

        // 取消任务
        fun remove(task: Runnable) {
            if (mPool != null && !mPool?.isShutdown!!) {
                mPool?.queue?.remove(task)
            }
        }
    }
}