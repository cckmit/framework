package org.mickey.framework.dbinspector.common;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class ThreadPoolExecutor extends ThreadPoolTaskExecutor {
    private static String THREAD_GROUP_NAME = "MIC-ThreadPool";

    private ThreadPoolExecutor() {super();}
    public static ThreadPoolExecutor newThreadPool(){return newThreadPool(Integer.MAX_VALUE);}

    public static ThreadPoolExecutor newThreadPool(int coreSize) {return newThreadPool(coreSize, coreSize);}

    public static ThreadPoolExecutor newThreadPool(int coreSize, int maxSize) {
        return newThreadPool(coreSize, maxSize, Integer.MAX_VALUE);
    }

    public static ThreadPoolExecutor newThreadPool(int coreSize, int maxSize, int queueCapacity) {
        return newThreadPool(coreSize, maxSize, queueCapacity, Integer.MAX_VALUE);
    }

    public static ThreadPoolExecutor newThreadPool(int coreSize, int maxSize, int queueCapacity, int keepAliveSeconds) {
        return createThreadPool(coreSize, maxSize, queueCapacity, keepAliveSeconds);
    }

    public static ThreadPoolExecutor newThreadPool(int nThreads, ThreadFactory threadFactory) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
        threadPoolExecutor.setCorePoolSize(nThreads);
        threadPoolExecutor.setThreadFactory(threadFactory);
        threadPoolExecutor.afterPropertiesSet();
        threadPoolExecutor.setThreadGroupName(THREAD_GROUP_NAME);
        return threadPoolExecutor;
    }

    private static ThreadPoolExecutor createThreadPool(int coreSize, int maxSize, int queueCapacity, int keepAliveSeconds) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
        threadPoolExecutor.setCorePoolSize(coreSize);
        threadPoolExecutor.setMaxPoolSize(maxSize);
        threadPoolExecutor.setQueueCapacity(queueCapacity);
        threadPoolExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolExecutor.setThreadGroupName(THREAD_GROUP_NAME);
        threadPoolExecutor.afterPropertiesSet();
        return threadPoolExecutor;
    }

    public static ThreadPoolExecutor createThreadPool(int coreSize, int maxSize, int queueCapacity, int keepAliveSeconds, ThreadFactory threadFactory) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
        threadPoolExecutor.setCorePoolSize(coreSize);
        threadPoolExecutor.setMaxPoolSize(maxSize);
        threadPoolExecutor.setQueueCapacity(queueCapacity);
        threadPoolExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolExecutor.setThreadGroupName(THREAD_GROUP_NAME);
        threadPoolExecutor.afterPropertiesSet();
        threadPoolExecutor.setThreadFactory(threadFactory);
        return threadPoolExecutor;
    }

    @Override
    public void execute(final Runnable task) {
        final Map<String, String> contextMap = SystemContext.getContextMap();
        RunProxy wrapper = new RunProxy(contextMap, task);
        super.execute(wrapper);
    }

    @Override
    public void execute(final Runnable task, long startTimeout) {
        final Map<String, String> contextMap = SystemContext.getContextMap();
        RunProxy wrapper = new RunProxy(contextMap, task);
        super.execute(wrapper, startTimeout);
    }

    @Override
    public Future<?> submit(final Runnable task) {
        final Map<String, String> contextMap = SystemContext.getContextMap();
        RunProxy wrapper = new RunProxy(contextMap, task);
        return super.submit(wrapper);
    }

    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        final Map<String, String> contextMap = SystemContext.getContextMap();
        CallProxy<T> wrapper = new CallProxy<T>(contextMap, task);
        return super.submit(wrapper);
    }

    private static class RunProxy implements Runnable {
        private Map<String, String> contextMap;
        private Runnable runner;

        public RunProxy(Map<String, String> contextMap, Runnable runner) {
            if (contextMap != null) {
                this.contextMap = new HashMap<>();
                this.contextMap.putAll(contextMap);
            }
            this.runner = runner;
        }

        @Override
        public void run() {
            try {
                if (contextMap != null) {
                    for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                        SystemContext.put(entry.getKey(), entry.getValue());
                    }
                }
                runner.run();
            } finally {
                SystemContext.clean();
            }
        }
    }

    private static class CallProxy<T> implements Callable<T> {
        private Map<String, String> contextMap;
        private Callable<T> caller;

        public CallProxy(Map<String, String> contextMap, Callable<T> caller) {
            if (contextMap != null) {
                this.contextMap = new HashMap<>();
                this.contextMap.putAll(contextMap);
            }
            this.caller = caller;
        }

        @Override
        public T call() throws Exception {
            try {
                if (contextMap != null) {
                    for (Map.Entry<String, String> entry : contextMap.entrySet()) {
                        SystemContext.put(entry.getKey(), entry.getValue());
                    }
                }
                return caller.call();
            } finally {
                SystemContext.clean();
            }
        }
    }
}
