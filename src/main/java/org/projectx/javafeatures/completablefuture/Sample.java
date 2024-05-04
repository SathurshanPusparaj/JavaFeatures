package org.projectx.javafeatures.completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * -x-F-x-F--------------x-F-x--
 *P         \          /
 * ---------E-F-E-F-E-F---------
 *
 * Stream                      completable future
 * zero, one, more data        at most have one data or error
 * foreach()                   thenAccept()
 *                             thenRun() -> like finalizer in object (execute once pipeline completed(useful for logging and run some optional operation etc))
 *
 * map()                       thenApply()
 *
 *                             exceptionally(throwable -> )
 *
 *                             future.completeOnTimeout(500, 3, TimeUnit.SECONDS). if this won't provide the future will wait(500 is result)
 *                             future.orTimeout(3, TimeUnit.SECONDS). if this send exception after 3 seconds if future won't return any value (if the future respond after 3s
 *                             it simply ignores it because it is already marked as completed) //most of the time use time out
 *
 * reduce()                    thenCombine() . combine 2 (completableFuture, async operation) - create(2).thenCombine(create(3), (a,b) -> a+b).thenAccept(System.out::println);
 *
 * flatMap()                   thenCompose(). returns another completableFuture - create(2).thenCompose(data -> create(data)).thenAccept(System.out::println);
 *
 *                             when carefully cancel() because after cancelled also the underlying thread can run the process.
 *                             use completeOnTimeout(), orTimeOut() or scheduled thread pool with acceptEither()
 *
 *                             allOf() - [void] process multiple completableFutures at the sametime but the problem us allof will wait until all completableFutures should complete.
 *                             event though one of them return exception immediately. To make it failfast check allOfFailfast(CompletableFuture<?>[] futures) method
 *
 *                             anyOf() - [datatype] returns the result which one give the result quickly.
 *
 *                             get() vs join() both behaviours are same [wait and return result] but get() returns CheckedException and join() returns UncheckedException
 *                             Interrupt get() and then throws an InterruptedException
 *                             get() method allows to specify the maximum wait time (cf.get(1000, TimeUnit.MILLISECONDS))
 *
 *                             cf1.applyToEither(cf2, i ->i).join() returns either cf1 or cf2 result
 *
 *                             cf1.complete(2); complete the cf1 with 2
 *
 *                             cf1.completeExceptionally(new NullPointerException());complete the cf1 with exception
 *
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * Once CompletableFuture is created (act like BehavioralSubject() - at most have one data or error- it keeps the emitted value in the as future object)
 * it will start processing (new CompletableFuture(), CompletableFuture.runAsync, CompletableFuture.supplyAsync)
 *
 * You can create multiple branches of CompletableFuture
 * AllAsync methods are (thenAcceptAsync,thenRunAsync etc) there for to use ExecutorService
 * without async method will run on ForkJoinPool
 * Best practice always use ThreadPool (ExecutorService) check the threadProblemWithCompletableFuture() for more info
 */
public class Sample {

    private static <T> CompletableFuture<Void> allOfFailfast(CompletableFuture<?>[] futures) {
        CompletableFuture<Void> result = CompletableFuture.allOf(futures);
        for (CompletableFuture<?> future : futures) {
            future.whenComplete((t, ex) -> {
                if (ex != null) {
                    result.completeExceptionally(ex);
                }
            });
        }

        return result;
    }

    public static void threadProblemWithCompletableFuture() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync is run by [" + Thread.currentThread().getName() + "] Thread");
            return 42;
        }).thenRun(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Holding [" + Thread.currentThread().getName() + "] Thread for 5s");
        }).join();

        //output
        //supplyAsync is run by [ForkJoinPool.commonPool-worker-1] Thread
        //Holding [main] Thread for 5s

        //So if we didn't provide a thread pool then it will use the main thread which might cause different issues(what if the thread blocked for long time).
        //so always use thread pool

        ExecutorService service = Executors.newFixedThreadPool(1);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync is run by [" + Thread.currentThread().getName() + "] Thread");
            return 42;
        }).thenRunAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Holding [" + Thread.currentThread().getName() + "] Thread for 5s");
        }, service).join();

        //supplyAsync is run by [ForkJoinPool.commonPool-worker-1] Thread
        //Holding [pool-1-thread-1] Thread for 5s
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
        //CompletableFuture.supplyAsync(() -> 4);
        CompletableFuture<Integer> future = new CompletableFuture<>();
        //future.completeOnTimeout(500, 3, TimeUnit.SECONDS);
        future.orTimeout( 3, TimeUnit.SECONDS);


        future.exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return Integer.valueOf("28");
        }).thenAccept(System.out::println);

        //future.complete(4);
        //future.completeExceptionally(new RuntimeException("some thing went wrong"));

        Thread.sleep(5000);

        future.complete(4);*/

        //create(2).thenCombine(create(3), (a,b) -> a+b).thenAccept(System.out::println);

        //create(2).thenCompose(data -> create(data)).thenAccept(System.out::println);

        //runAsync();
        /*CompletableFuture<List<String>> listCompletableFuture = supplyAsync();
        for (int i =0; i< 100; i++) {
            System.out.println(i);
            Thread.sleep(100);
        }
        try {
            System.out.println(listCompletableFuture.get());
        } catch (Exception e) {

        }

        System.out.println(listCompletableFuture.isCompletedExceptionally());
        System.out.println(listCompletableFuture.get());*/

        threadProblemWithCompletableFuture();
    }

    public static void runAsync() throws ExecutionException, InterruptedException {
        List<String> names = List.of("rajini", "kamal", "ajith", "vijay", "surya");

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            for (String name : names) {
                System.out.println("name : " + name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //return completableFuture.get() converts async -> sync
       // return completableFuture.toCompletableFuture();
    }

    public static CompletableFuture<List<String>> supplyAsync() throws ExecutionException, InterruptedException {
        List<String> names = List.of("rajini", "kamal", "ajith", "vijay", "surya");

        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("executing supply async");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            names.forEach(System.out::print);
            return names;
        });
        return completableFuture;
    }

    public static CompletableFuture<Integer> create(int n) {
        return CompletableFuture.supplyAsync(() -> n);
    }
}
