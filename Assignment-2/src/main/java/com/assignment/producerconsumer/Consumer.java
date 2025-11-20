package com.assignment.producerconsumer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Consumer thread - reads items from shared queue and stores in destination.
 * Demonstrates thread synchronization, blocking queues, and wait/notify mechanism.
 */
public class Consumer implements Runnable {
    
    private final BlockingQueue<String> sharedQueue;
    private final List<String> destinationContainer;
    private final Object lock;
    private final AtomicBoolean productionComplete;
    
    public Consumer(BlockingQueue<String> sharedQueue,
                   List<String> destinationContainer,
                   Object lock,
                   AtomicBoolean productionComplete) {
        this.sharedQueue = sharedQueue;
        this.destinationContainer = destinationContainer;
        this.lock = lock;
        this.productionComplete = productionComplete;
    }
    
    @Override
    public void run() {
        System.out.println("[CONSUMER] Starting consumer thread...");
        
        try {
            while (true) {
                synchronized (lock) {
                    // Exit if production done and queue empty
                    if (productionComplete.get() && sharedQueue.isEmpty()) {
                        System.out.println("[CONSUMER] No more items to consume. Exiting.");
                        break;
                    }
                    
                    // Queue empty - wait for producer
                    if (sharedQueue.isEmpty()) {
                        System.out.println("[CONSUMER] Queue is empty, waiting for items...");
                        lock.wait(1000);
                        continue;
                    }
                }
                
                // Get item from queue (non-blocking)
                String item = sharedQueue.poll();
                
                if (item != null) {
                    System.out.println("[CONSUMER] Consuming item: " + item);
                    
                    synchronized (lock) {
                        destinationContainer.add(item);
                        System.out.println("[CONSUMER] Added '" + item + "' to destination. Total items: " + 
                                         destinationContainer.size());
                        
                        // Notify producer
                        lock.notifyAll();
                    }
                    
                    // Simulate consumption time (outside synchronized block)
                    Thread.sleep((long)(Math.random() * 500 + 200));
                }
            }
            
            System.out.println("[CONSUMER] Consumption complete. Exiting consumer thread.");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[CONSUMER] Thread interrupted: " + e.getMessage());
        }
    }
}
