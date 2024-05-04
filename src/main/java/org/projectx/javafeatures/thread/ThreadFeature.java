package org.projectx.javafeatures.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadFeature {

    public static void main(String[] args) throws InterruptedException {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

        }

        Thread thread = Thread.ofVirtual().unstarted(() -> System.out.println("Hello"));
        thread.start();
        thread.join();
    }
}
