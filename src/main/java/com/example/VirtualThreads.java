package com.example;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

public class VirtualThreads {

    private final static Logger logger = LoggerFactory.getLogger(VirtualThreads.class);

    private static void log(String message) {
        logger.info("{} |" + message, Thread.currentThread());
    }

    // converts checked exception to unchecked
    @SneakyThrows
    public static void sleep(Duration duration) {
        Thread.sleep(duration.toSeconds());
    }

    private static void oomExample() {
        for (var i = 0; i < 100_000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(1L).toMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            ).start();
        }
    }

    // virtual thread = new thread that the JVM will schedule on platform thread.
    private static Thread virtualThread(String name, Runnable runnable) {
        return Thread.ofVirtual()
                .name(name)
                .start(runnable);
    }

    // morning routine - boil some water while I am talking a shower
    static Thread bathTime() {
        return virtualThread("Bath Time",
                () -> {
                    logger.info("I'm going to take a bath");
                    sleep(Duration.ofSeconds(100L));
                    logger.info("I'm done with the bath");
                });
    }

    static Thread boilingWeather() {
        return virtualThread("Bath Time",
                () -> {
                    logger.info("I'm going to boil water for some tee");
                    sleep(Duration.ofSeconds(1000L));
                    logger.info("I'm done with the water");
                });
    }

    @SneakyThrows
    static void concurrentMorningRoutine() {
        var bathTime = bathTime();
        var boilingWater = boilingWeather();
        bathTime.join();
        boilingWater.join();
    }

    @SneakyThrows
    static void concurrentMorningRoutineWithExecutors() {
        final ThreadFactory threadFactory = Thread.ofVirtual().name("routine-", 0).factory();
        try(var executors = Executors.newThreadPerTaskExecutor(threadFactory)) {
            var bathTime = executors.submit(
                    () -> {
                        logger.info("I'm going to take a bath");
                        sleep(Duration.ofSeconds(100L));
                        logger.info("I'm done with the bath");
                    });
            var boilingWater = executors.submit(
                    () -> {
                        logger.info("I'm going to boil water for some tee");
                        sleep(Duration.ofSeconds(1000L));
                        logger.info("I'm done with the water");
                    });

            bathTime.get();
            boilingWater.get();
        }
    }

    static int numberOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    static void viewCarrierThreadPoolSize() {
        final ThreadFactory factory = Thread.ofVirtual().name("routine-", 0).factory();
        try(var executor = Executors.newThreadPerTaskExecutor(factory)) {
            IntStream.range(0, numberOfCores() + 1)
                    .forEach(i -> executor.submit( () -> {
                        logger.info("Hi from virtual thread " + i);
                        sleep(Duration.ofSeconds(1L));
                    }));
        }
    }

    public static void main(String[] args) {
        viewCarrierThreadPoolSize();
    }

}
