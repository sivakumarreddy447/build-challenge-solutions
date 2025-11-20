package com.assignment.producerconsumer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Producer thread - reads items from source and adds to shared queue.
 * Demonstrates thread synchronization, blocking queues, and wait/notify mechanism.
 */
public class Producer implements Runnable {
    
    private final List<String> sourceContainer;
    private final BlockingQueue<String> sharedQueue;
    private final Object lock;
    private final AtomicBoolean productionComplete;
    private int sourceIndex = 0;
    
    public Producer(List<String> sourceContainer, 
                   BlockingQueue<String> sharedQueue, 
                   Object lock,
                   AtomicBoolean productionComplete) {
        this.sourceContainer = sourceContainer;
        this.sharedQueue = sharedQueue;
        this.lock = lock;
        this.productionComplete = productionComplete;
    }
    
    @Override
    public void run() {
        System.out.println("[PRODUCER] Starting producer thread...");
        
        try {
            while (sourceIndex < sourceContainer.size()) {
                synchronized (lock) {
                    String item = sourceContainer.get(sourceIndex);
                    sourceIndex++;
                    
                    System.out.println("[PRODUCER] Producing item: " + item);
                    
                    // Try adding to queue (non-blocking)
                    boolean added = sharedQueue.offer(item);
                    
                    if (!added) {
                        // Queue full - wait for consumer
                        System.out.println("[PRODUCER] Queue is full, waiting...");
                        sourceIndex--; // Retry this item
                        lock.wait(1000);
                        continue;
                    }
                    
                    System.out.println("[PRODUCER] Added '" + item + "' to queue. Queue size: " + 
                                     sharedQueue.size());
                    
                    // Notify consumer
                    lock.notifyAll();
                }
                
                // Simulate production time (outside synchronized block)
                Thread.sleep((long)(Math.random() * 400 + 100));
            }
            
            // Signal completion
            synchronized (lock) {
                productionComplete.set(true);
                lock.notifyAll();
            }
            
            System.out.println("[PRODUCER] Production complete. Exiting producer thread.");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[PRODUCER] Thread interrupted: " + e.getMessage());
        }
    }
}
