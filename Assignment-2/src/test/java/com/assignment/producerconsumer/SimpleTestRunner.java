package com.assignment.producerconsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test suite for Producer-Consumer pattern.
 * Tests thread synchronization, concurrent programming, blocking queues, and wait/notify.
 * 
 * Usage: java SimpleTestRunner [demo]
 */
public class SimpleTestRunner {
    
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    private static int totalTests = 0;
    
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("demo")) {
            runDemo();
            return;
        }
        System.out.println("+" + "=".repeat(58) + "+");
        System.out.println("|" + center("PRODUCER-CONSUMER TEST SUITE", 58) + "|");
        System.out.println("|" + center("Simple Test Runner (No JUnit Required)", 58) + "|");
        System.out.println("+" + "=".repeat(58) + "+");
        System.out.println();
        
        try {
            test1_BasicProducerConsumer();
            test2_ThreadSynchronization();
            test3_SmallQueueCapacity();
            test4_LargeQueueCapacity();
            test5_EmptySource();
            test6_SingleItem();
            test7_ConcurrentExecution();
            test8_ProducerWaitsWhenQueueFull();
            test9_ConsumerWaitsWhenQueueEmpty();
            test10_MultipleRuns();
            test11_StressTest();
            test12_GracefulShutdown();
            test13_DataIntegrity();
            test14_QueueState();
            test15_PerformanceComparison();
            test16_MultipleProducersSingleConsumer();
            test17_SingleProducerMultipleConsumers();
            test18_MultipleProducersMultipleConsumers();
            
            printSummary();
            
        } catch (Exception e) {
            System.err.println("FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        System.exit(testsFailed > 0 ? 1 : 0);
    }
    
    /**
     * Demo mode: runs producer-consumer with 10 items and queue capacity of 3.
     */
    static void runDemo() {
        System.out.println("============================================================");
        System.out.println("PRODUCER-CONSUMER PATTERN DEMONSTRATION");
        System.out.println("============================================================");
        
        try {
            List<String> sourceData = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                sourceData.add("Item-" + i);
            }
            
            System.out.println("Source container: " + sourceData);
            System.out.println("Queue capacity: 3");
            System.out.println("============================================================");
            
            BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<>(3);
            List<String> destinationContainer = new ArrayList<>();
            Object lock = new Object();
            AtomicBoolean productionComplete = new AtomicBoolean(false);
            
            Producer producer = new Producer(sourceData, sharedQueue, lock, productionComplete);
            Consumer consumer = new Consumer(sharedQueue, destinationContainer, lock, productionComplete);
            
            Thread producerThread = new Thread(producer, "Producer");
            Thread consumerThread = new Thread(consumer, "Consumer");
            
            producerThread.start();
            consumerThread.start();
            
            producerThread.join();
            consumerThread.join();
            
            System.out.println("============================================================");
            System.out.println("FINAL RESULTS");
            System.out.println("============================================================");
            System.out.println("Source container: " + sourceData);
            System.out.println("Destination container: " + destinationContainer);
            System.out.println("Items transferred: " + destinationContainer.size() + "/" + sourceData.size());
            System.out.println("Queue size: " + sharedQueue.size());
            System.out.println("============================================================");
            
            if (destinationContainer.equals(sourceData)) {
                System.out.println("[SUCCESS] All items transferred correctly!");
            } else {
                System.out.println("[ERROR] Items mismatch!");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Demo interrupted: " + e.getMessage());
        }
    }
    
    /**
     * Helper class to manage producer-consumer system for testing.
     */
    static class ProducerConsumerSystem {
        final List<String> sourceContainer;
        final BlockingQueue<String> sharedQueue;
        final List<String> destinationContainer;
        final Object lock;
        final AtomicBoolean productionComplete;
        
        ProducerConsumerSystem(List<String> sourceData, int queueCapacity) {
            this.sourceContainer = new ArrayList<>(sourceData);
            this.sharedQueue = new LinkedBlockingQueue<>(queueCapacity);
            this.destinationContainer = new ArrayList<>();
            this.lock = new Object();
            this.productionComplete = new AtomicBoolean(false);
        }
        
        void run() throws InterruptedException {
            Producer producer = new Producer(sourceContainer, sharedQueue, lock, productionComplete);
            Consumer consumer = new Consumer(sharedQueue, destinationContainer, lock, productionComplete);
            
            Thread producerThread = new Thread(producer, "Producer");
            Thread consumerThread = new Thread(consumer, "Consumer");
            
            producerThread.start();
            consumerThread.start();
            
            producerThread.join();
            consumerThread.join();
        }
        
        List<String> getDestinationContainer() {
            return new ArrayList<>(destinationContainer);
        }
        
        BlockingQueue<String> getSharedQueue() {
            return sharedQueue;
        }
    }
    
    /** Test 1: Basic producer-consumer with 10 items */
    static void test1_BasicProducerConsumer() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 1] Basic Producer-Consumer with 10 items");
        
        List<String> sourceData = createTestData(10);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 5);
        pc.run();
        
        List<String> dest = pc.getDestinationContainer();
        
        assertCondition(dest.size() == sourceData.size(), 
                       "All items transferred", 
                       "Expected " + sourceData.size() + " items, got " + dest.size());
        assertCondition(dest.equals(sourceData), 
                       "Items in correct order", 
                       "Order mismatch");
        assertCondition(pc.getSharedQueue().isEmpty(), 
                       "Queue is empty", 
                       "Queue not empty");
        
        passTest("Test 1");
    }
    
    /** Test 2: Thread synchronization - no race conditions with 50 items */
    static void test2_ThreadSynchronization() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 2] Thread Synchronization - No race conditions");
        
        List<String> sourceData = createTestData(50);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 10);
        pc.run();
        
        List<String> dest = pc.getDestinationContainer();
        long uniqueCount = dest.stream().distinct().count();
        
        assertCondition(dest.size() == sourceData.size(), 
                       "All items transferred", 
                       "Item count mismatch");
        assertCondition(uniqueCount == sourceData.size(), 
                       "No duplicates (no race condition)", 
                       "Found " + (sourceData.size() - uniqueCount) + " duplicates");
        assertCondition(dest.equals(sourceData), 
                       "Order preserved", 
                       "Order not preserved");
        
        passTest("Test 2");
    }
    
    /** Test 3: Small queue capacity (1) - tests blocking */
    static void test3_SmallQueueCapacity() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 3] Small Queue Capacity (capacity=1)");
        
        List<String> sourceData = createTestData(20);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 1);
        
        long startTime = System.currentTimeMillis();
        pc.run();
        long duration = System.currentTimeMillis() - startTime;
        
        List<String> dest = pc.getDestinationContainer();
        
        assertCondition(dest.equals(sourceData), 
                       "All items transferred despite small queue", 
                       "Items mismatch");
        
        System.out.println("  [TIME] Duration: " + duration + "ms");
        passTest("Test 3");
    }
    
    /** Test 4: Large queue capacity (100) - minimal blocking */
    static void test4_LargeQueueCapacity() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 4] Large Queue Capacity (capacity=100)");
        
        List<String> sourceData = createTestData(10);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 100);
        pc.run();
        
        List<String> dest = pc.getDestinationContainer();
        
        assertCondition(dest.equals(sourceData), 
                       "All items transferred with large queue", 
                       "Items mismatch");
        
        passTest("Test 4");
    }
    
    /** Test 5: Edge case - empty source */
    static void test5_EmptySource() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 5] Edge Case - Empty source");
        
        List<String> sourceData = new ArrayList<>();
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 5);
        pc.run();
        
        assertCondition(pc.getDestinationContainer().isEmpty(), 
                       "Destination is empty", 
                       "Destination should be empty");
        assertCondition(pc.getSharedQueue().isEmpty(), 
                       "Queue is empty", 
                       "Queue should be empty");
        
        passTest("Test 5");
    }
    
    /** Test 6: Edge case - single item */
    static void test6_SingleItem() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 6] Edge Case - Single item");
        
        List<String> sourceData = List.of("SingleItem");
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 5);
        pc.run();
        
        List<String> dest = pc.getDestinationContainer();
        
        assertCondition(dest.size() == 1, 
                       "One item transferred", 
                       "Expected 1 item");
        assertCondition("SingleItem".equals(dest.get(0)), 
                       "Correct item value", 
                       "Item value mismatch");
        
        passTest("Test 6");
    }
    
    /** Test 7: Concurrent execution with 100 items */
    static void test7_ConcurrentExecution() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 7] Concurrent Execution - Performance check");
        
        List<String> sourceData = createTestData(100);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 10);
        
        long startTime = System.currentTimeMillis();
        pc.run();
        long duration = System.currentTimeMillis() - startTime;
        
        assertCondition(pc.getDestinationContainer().size() == 100, 
                       "All 100 items transferred", 
                       "Item count mismatch");
        
        System.out.println("  [TIME] Completed 100 items in " + duration + "ms");
        passTest("Test 7");
    }
    
    /** Test 8: Producer wait mechanism when queue is full */
    static void test8_ProducerWaitsWhenQueueFull() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 8] Producer wait mechanism (small queue)");
        
        List<String> sourceData = createTestData(30);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 2);
        pc.run();
        
        assertCondition(pc.getDestinationContainer().equals(sourceData), 
                       "All items transferred despite queue blocking", 
                       "Items mismatch");
        
        passTest("Test 8");
    }
    
    /** Test 9: Consumer wait mechanism when queue is empty */
    static void test9_ConsumerWaitsWhenQueueEmpty() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 9] Consumer wait mechanism");
        
        List<String> sourceData = createTestData(15);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 5);
        pc.run();
        
        assertCondition(pc.getDestinationContainer().equals(sourceData), 
                       "Consumer waits and all items transferred", 
                       "Items mismatch");
        
        passTest("Test 9");
    }
    
    /** Test 10: Multiple consecutive runs - thread safety */
    static void test10_MultipleRuns() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 10] Thread Safety - 5 consecutive runs");
        
        for (int i = 1; i <= 5; i++) {
            System.out.print("  Run " + i + "/5... ");
            List<String> sourceData = createTestData(20);
            ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 5);
            pc.run();
            
            assertCondition(pc.getDestinationContainer().equals(sourceData), 
                           "Run " + i + " successful", 
                           "Run " + i + " failed");
            System.out.println("[OK]");
        }
        
        passTest("Test 10");
    }
    
    /** Test 11: Stress test with 500 items */
    static void test11_StressTest() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 11] Stress Test - 500 items");
        
        List<String> sourceData = createTestData(500);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 20);
        
        long startTime = System.currentTimeMillis();
        pc.run();
        long duration = System.currentTimeMillis() - startTime;
        
        assertCondition(pc.getDestinationContainer().size() == 500, 
                       "All 500 items transferred", 
                       "Item count mismatch");
        assertCondition(pc.getDestinationContainer().equals(sourceData), 
                       "Order preserved", 
                       "Order mismatch");
        
        System.out.println("  [TIME] Processed 500 items in " + duration + "ms");
        passTest("Test 11");
    }
    
    /** Test 12: Graceful shutdown - no hanging threads */
    static void test12_GracefulShutdown() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 12] Graceful Shutdown - No hanging threads");
        
        int threadsBefore = Thread.activeCount();
        
        List<String> sourceData = createTestData(25);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 5);
        pc.run();
        
        Thread.sleep(500); // Allow cleanup
        
        int threadsAfter = Thread.activeCount();
        
        assertCondition(pc.getDestinationContainer().size() == 25, 
                       "All items transferred", 
                       "Item count mismatch");
        assertCondition(threadsAfter <= threadsBefore + 2, 
                       "Threads terminated gracefully", 
                       "Potential thread leak");
        
        passTest("Test 12");
    }
    
    /** Test 13: Data integrity with special characters */
    static void test13_DataIntegrity() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 13] Data Integrity - Special characters");
        
        List<String> sourceData = List.of(
            "Normal-Item",
            "Item with spaces",
            "Item_with_underscore",
            "Item-with-dashes",
            "Item123WithNumbers",
            "UPPERCASE_ITEM",
            "lowercase_item",
            "MixedCaseItem",
            "Item@with#special$chars",
            ""
        );
        
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 3);
        pc.run();
        
        assertCondition(pc.getDestinationContainer().equals(sourceData), 
                       "All data types preserved correctly", 
                       "Data integrity issue");
        
        passTest("Test 13");
    }
    
    /** Test 14: Final queue state verification */
    static void test14_QueueState() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 14] Final queue state verification");
        
        List<String> sourceData = createTestData(30);
        ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, 8);
        pc.run();
        
        assertCondition(pc.getSharedQueue().isEmpty(), 
                       "Queue is empty", 
                       "Queue not empty");
        assertCondition(pc.getSharedQueue().size() == 0, 
                       "Queue size is 0", 
                       "Queue size not 0");
        
        passTest("Test 14");
    }
    
    /** Test 15: Performance comparison across different queue capacities */
    static void test15_PerformanceComparison() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 15] Performance - Different queue capacities");
        
        int[] capacities = {1, 5, 10, 50};
        List<String> sourceData = createTestData(100);
        
        for (int capacity : capacities) {
            ProducerConsumerSystem pc = new ProducerConsumerSystem(sourceData, capacity);
            
            long startTime = System.currentTimeMillis();
            pc.run();
            long duration = System.currentTimeMillis() - startTime;
            
            assertCondition(pc.getDestinationContainer().size() == 100, 
                           "Capacity " + capacity + " works", 
                           "Failed with capacity " + capacity);
            
            System.out.println("  [PERF] Capacity " + String.format("%2d", capacity) + 
                             ": " + String.format("%4d", duration) + "ms");
        }
        
        passTest("Test 15");
    }
    
    /** Test 16: Multiple producers (3) + single consumer */
    static void test16_MultipleProducersSingleConsumer() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 16] Multiple Producers (3) + Single Consumer");
        
        // Create separate source data for each producer
        List<String> source1 = IntStream.range(1, 21).mapToObj(i -> "P1-Item-" + i).collect(Collectors.toList());
        List<String> source2 = IntStream.range(1, 21).mapToObj(i -> "P2-Item-" + i).collect(Collectors.toList());
        List<String> source3 = IntStream.range(1, 21).mapToObj(i -> "P3-Item-" + i).collect(Collectors.toList());
        
        List<String> allExpectedItems = new ArrayList<>();
        allExpectedItems.addAll(source1);
        allExpectedItems.addAll(source2);
        allExpectedItems.addAll(source3);
        
        BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<>(10);
        List<String> destinationContainer = new ArrayList<>();
        Object lock = new Object();
        AtomicBoolean p1Complete = new AtomicBoolean(false);
        AtomicBoolean p2Complete = new AtomicBoolean(false);
        AtomicBoolean p3Complete = new AtomicBoolean(false);
        AtomicBoolean allProductionComplete = new AtomicBoolean(false);
        
        Producer producer1 = new Producer(source1, sharedQueue, lock, p1Complete);
        Producer producer2 = new Producer(source2, sharedQueue, lock, p2Complete);
        Producer producer3 = new Producer(source3, sharedQueue, lock, p3Complete);
        
        Consumer consumer = new Consumer(sharedQueue, destinationContainer, lock, allProductionComplete);
        
        // Create threads
        Thread p1Thread = new Thread(producer1, "Producer-1");
        Thread p2Thread = new Thread(producer2, "Producer-2");
        Thread p3Thread = new Thread(producer3, "Producer-3");
        Thread consumerThread = new Thread(consumer, "Consumer");
        
        p1Thread.start();
        p2Thread.start();
        p3Thread.start();
        consumerThread.start();
        
        p1Thread.join();
        p2Thread.join();
        p3Thread.join();
        
        synchronized (lock) {
            allProductionComplete.set(true);
            lock.notifyAll();
        }
        
        consumerThread.join();
        
        assertCondition(destinationContainer.size() == allExpectedItems.size(),
                       "All items from all producers transferred",
                       "Expected " + allExpectedItems.size() + " items, got " + destinationContainer.size());
        
        long uniqueCount = destinationContainer.stream().distinct().count();
        assertCondition(uniqueCount == destinationContainer.size(),
                       "No duplicate items (no race condition)",
                       "Found " + (destinationContainer.size() - uniqueCount) + " duplicates");
        
        assertCondition(destinationContainer.containsAll(allExpectedItems),
                       "All expected items present",
                       "Some items missing");
        
        assertCondition(sharedQueue.isEmpty(),
                       "Queue is empty",
                       "Queue not empty");
        
        System.out.println("  [INFO] Successfully coordinated 3 producers + 1 consumer");
        passTest("Test 16");
    }
    
    /** Test 17: Single producer + multiple consumers (3) */
    static void test17_SingleProducerMultipleConsumers() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 17] Single Producer + Multiple Consumers (3)");
        
        List<String> sourceData = createTestData(60);
        
        BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<>(10);
        List<String> destination1 = new ArrayList<>();
        List<String> destination2 = new ArrayList<>();
        List<String> destination3 = new ArrayList<>();
        Object lock = new Object();
        AtomicBoolean productionComplete = new AtomicBoolean(false);
        
        Producer producer = new Producer(sourceData, sharedQueue, lock, productionComplete);
        Consumer consumer1 = new Consumer(sharedQueue, destination1, lock, productionComplete);
        Consumer consumer2 = new Consumer(sharedQueue, destination2, lock, productionComplete);
        Consumer consumer3 = new Consumer(sharedQueue, destination3, lock, productionComplete);
        
        Thread producerThread = new Thread(producer, "Producer");
        Thread c1Thread = new Thread(consumer1, "Consumer-1");
        Thread c2Thread = new Thread(consumer2, "Consumer-2");
        Thread c3Thread = new Thread(consumer3, "Consumer-3");
        
        producerThread.start();
        c1Thread.start();
        c2Thread.start();
        c3Thread.start();
        
        producerThread.join();
        c1Thread.join();
        c2Thread.join();
        c3Thread.join();
        
        List<String> allConsumed = new ArrayList<>();
        allConsumed.addAll(destination1);
        allConsumed.addAll(destination2);
        allConsumed.addAll(destination3);
        
        assertCondition(allConsumed.size() == sourceData.size(),
                       "All items consumed across all consumers",
                       "Expected " + sourceData.size() + " items, got " + allConsumed.size());
        
        long uniqueCount = allConsumed.stream().distinct().count();
        assertCondition(uniqueCount == allConsumed.size(),
                       "No item consumed twice (thread-safe consumption)",
                       "Found " + (allConsumed.size() - uniqueCount) + " duplicates");
        
        assertCondition(allConsumed.containsAll(sourceData),
                       "All source items consumed",
                       "Some items missing");
        
        assertCondition(sharedQueue.isEmpty(),
                       "Queue is empty",
                       "Queue not empty");
        
        System.out.println("  [INFO] Work distribution: C1=" + destination1.size() + 
                         ", C2=" + destination2.size() + ", C3=" + destination3.size());
        System.out.println("  [INFO] Successfully coordinated 1 producer + 3 consumers");
        passTest("Test 17");
    }
    
    /** Test 18: Multiple producers (2) + multiple consumers (2) */
    static void test18_MultipleProducersMultipleConsumers() throws InterruptedException {
        totalTests++;
        System.out.println("\n[Test 18] Multiple Producers (2) + Multiple Consumers (2)");
        
        List<String> source1 = IntStream.range(1, 31).mapToObj(i -> "P1-Item-" + i).collect(Collectors.toList());
        List<String> source2 = IntStream.range(1, 31).mapToObj(i -> "P2-Item-" + i).collect(Collectors.toList());
        
        List<String> allExpectedItems = new ArrayList<>();
        allExpectedItems.addAll(source1);
        allExpectedItems.addAll(source2);
        
        BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<>(15);
        List<String> destination1 = new ArrayList<>();
        List<String> destination2 = new ArrayList<>();
        Object lock = new Object();
        AtomicBoolean p1Complete = new AtomicBoolean(false);
        AtomicBoolean p2Complete = new AtomicBoolean(false);
        AtomicBoolean allProductionComplete = new AtomicBoolean(false);
        
        Producer producer1 = new Producer(source1, sharedQueue, lock, p1Complete);
        Producer producer2 = new Producer(source2, sharedQueue, lock, p2Complete);
        Consumer consumer1 = new Consumer(sharedQueue, destination1, lock, allProductionComplete);
        Consumer consumer2 = new Consumer(sharedQueue, destination2, lock, allProductionComplete);
        
        Thread p1Thread = new Thread(producer1, "Producer-1");
        Thread p2Thread = new Thread(producer2, "Producer-2");
        Thread c1Thread = new Thread(consumer1, "Consumer-1");
        Thread c2Thread = new Thread(consumer2, "Consumer-2");
        
        p1Thread.start();
        p2Thread.start();
        c1Thread.start();
        c2Thread.start();
        
        p1Thread.join();
        p2Thread.join();
        
        synchronized (lock) {
            allProductionComplete.set(true);
            lock.notifyAll();
        }
        
        c1Thread.join();
        c2Thread.join();
        
        List<String> allConsumed = new ArrayList<>();
        allConsumed.addAll(destination1);
        allConsumed.addAll(destination2);
        
        assertCondition(allConsumed.size() == allExpectedItems.size(),
                       "All items from all producers consumed",
                       "Expected " + allExpectedItems.size() + " items, got " + allConsumed.size());
        
        long uniqueCount = allConsumed.stream().distinct().count();
        assertCondition(uniqueCount == allConsumed.size(),
                       "No duplicate items (thread-safe operations)",
                       "Found " + (allConsumed.size() - uniqueCount) + " duplicates");
        
        assertCondition(allConsumed.containsAll(allExpectedItems),
                       "All produced items consumed",
                       "Some items missing");
        
        assertCondition(sharedQueue.isEmpty(),
                       "Queue is empty",
                       "Queue not empty");
        
        System.out.println("  [INFO] Production: P1=" + source1.size() + ", P2=" + source2.size());
        System.out.println("  [INFO] Consumption: C1=" + destination1.size() + ", C2=" + destination2.size());
        System.out.println("  [INFO] Successfully coordinated 2 producers + 2 consumers");
        passTest("Test 18");
    }
    
    static List<String> createTestData(int count) {
        return IntStream.range(1, count + 1)
                       .mapToObj(i -> "Item-" + i)
                       .collect(Collectors.toList());
    }
    
    static void assertCondition(boolean condition, String message, String errorMsg) {
        if (condition) {
            System.out.println("  [OK] " + message);
        } else {
            System.out.println("  [FAIL] " + message + " - " + errorMsg);
            throw new AssertionError(errorMsg);
        }
    }
    
    static void passTest(String testName) {
        testsPassed++;
        System.out.println("  [PASS] " + testName + " PASSED");
    }
    
    static void failTest(String testName, Exception e) {
        testsFailed++;
        System.out.println("  [X] " + testName + " FAILED");
        System.out.println("     Error: " + e.getMessage());
        if (e.getCause() != null) {
            System.out.println("     Cause: " + e.getCause().getMessage());
        }
    }
    
    static String center(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + 
               " ".repeat(Math.max(0, width - text.length() - padding));
    }
    
    static void printSummary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("Total Tests:  " + totalTests);
        System.out.println("[PASS] Passed:    " + testsPassed);
        System.out.println("[FAIL] Failed:    " + testsFailed);
        System.out.println("=".repeat(60));
        
        if (testsFailed == 0) {
            System.out.println("*** ALL TESTS PASSED! ***");
            System.out.println("\nTesting Objectives Verified:");
            System.out.println("  * Thread Synchronization");
            System.out.println("  * Concurrent Programming");
            System.out.println("  * Blocking Queues");
            System.out.println("  * Wait/Notify Mechanism");
        } else {
            System.out.println("[WARNING] SOME TESTS FAILED");
            System.out.println("Review the output above for details.");
        }
        System.out.println("=".repeat(60));
    }
}

