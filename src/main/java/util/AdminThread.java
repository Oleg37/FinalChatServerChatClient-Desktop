package util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminThread {

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static final ExecutorService threadExecutorPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
}
