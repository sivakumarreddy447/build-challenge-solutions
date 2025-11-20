# Producer-Consumer Pattern

A Java implementation of the classic Producer-Consumer pattern demonstrating thread synchronization, concurrent programming, blocking queues, and wait/notify mechanisms.

## 📋 Assignment Overview

This project implements a concurrent data transfer system where:
- **Producer thread** reads items from a source container and places them into a shared queue
- **Consumer thread** reads items from the shared queue and stores them in a destination container
- Both threads synchronize using locks, blocking queues, and wait/notify mechanisms

## 🎯 Key Concepts Demonstrated

- ✅ **Thread Synchronization**: `synchronized` blocks and proper locking
- ✅ **Concurrent Programming**: Multiple threads running in parallel
- ✅ **Blocking Queues**: Thread-safe queue operations with capacity management
- ✅ **Wait/Notify Mechanism**: Efficient inter-thread communication
- ✅ **Multiple Producers/Consumers**: Advanced multi-threading scenarios

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6+ (optional)

### Build & Run

**Using Maven (Recommended):**
```bash
# Compile
mvn clean compile

# Run all 18 tests
mvn exec:java

# Run demo mode
mvn exec:java -Dexec.args="demo"

# Create JAR
mvn package
java -jar target/producer-consumer-1.0.0-jar-with-dependencies.jar
```

**Using Direct Java Compilation:**
```bash
# Compile
javac -d target/classes src/main/java/com/assignment/producerconsumer/*.java
javac -cp target/classes -d target/test-classes src/test/java/com/assignment/producerconsumer/*.java

# Run tests
java -cp target/classes:target/test-classes com.assignment.producerconsumer.SimpleTestRunner

# Run demo
java -cp target/classes:target/test-classes com.assignment.producerconsumer.SimpleTestRunner demo
```

## 📁 Project Structure

```
producer-consumer/
├── pom.xml                                  # Maven configuration
├── README.md                                # This file
├── src/
│   ├── main/java/com/assignment/producerconsumer/
│   │   ├── Producer.java                   # Producer thread implementation
│   │   └── Consumer.java                   # Consumer thread implementation
│   └── test/java/com/assignment/producerconsumer/
│       └── SimpleTestRunner.java           # 18 comprehensive tests
└── target/                                  # Build output (auto-generated)
```

## 🧪 Test Coverage (18 Tests)

### Basic Tests (1-15)
| Test | Description |
|------|-------------|
| 1 | Basic producer-consumer functionality |
| 2 | Thread synchronization - no race conditions |
| 3 | Small queue capacity (1) - blocking behavior |
| 4 | Large queue capacity (100) - minimal blocking |
| 5 | Edge case - empty source |
| 6 | Edge case - single item |
| 7 | Concurrent execution performance |
| 8 | Producer wait mechanism |
| 9 | Consumer wait mechanism |
| 10 | Multiple consecutive runs |
| 11 | Stress test with 500 items |
| 12 | Graceful shutdown - no hanging threads |
| 13 | Data integrity with special characters |
| 14 | Final queue state verification |
| 15 | Performance comparison across queue sizes |

### Advanced Tests (16-18)
| Test | Description |
|------|-------------|
| 16 | **Multiple Producers (3) → Single Consumer** |
| 17 | **Single Producer → Multiple Consumers (3)** |
| 18 | **Multiple Producers (2) → Multiple Consumers (2)** |

## 📊 Sample Output

### Demo Mode
```
============================================================
PRODUCER-CONSUMER PATTERN DEMONSTRATION
============================================================
Source container: [Item-1, Item-2, ..., Item-10]
Queue capacity: 3
============================================================
[PRODUCER] Starting producer thread...
[CONSUMER] Starting consumer thread...
[PRODUCER] Producing item: Item-1
[PRODUCER] Added 'Item-1' to queue. Queue size: 1
[CONSUMER] Consuming item: Item-1
...
============================================================
FINAL RESULTS
============================================================
Items transferred: 10/10
[SUCCESS] All items transferred correctly!
```

### Test Suite
```
+===========================================================+
|           PRODUCER-CONSUMER TEST SUITE                    |
|        Simple Test Runner (No JUnit Required)             |
+===========================================================+

[Test 1] Basic Producer-Consumer with 10 items
  [OK] All items transferred
  [OK] Items in correct order
  [OK] Queue is empty
  [PASS] Test 1 PASSED

...

============================================================
TEST SUMMARY
============================================================
Total Tests:  18
[PASS] Passed:    18
[FAIL] Failed:    0
============================================================
*** ALL TESTS PASSED! ***

Testing Objectives Verified:
  * Thread Synchronization
  * Concurrent Programming
  * Blocking Queues
  * Wait/Notify Mechanism
============================================================
```

## 🔧 Implementation Details

### Producer.java
- Reads items from source container sequentially
- Adds items to shared blocking queue using `offer()`
- Waits when queue is full using `lock.wait()`
- Notifies consumer when items are added
- Signals completion via `AtomicBoolean` flag

### Consumer.java
- Polls items from shared blocking queue
- Waits when queue is empty using `lock.wait()`
- Stores consumed items in destination container
- Notifies producer when space is available
- Exits when production is complete and queue is empty

### SimpleTestRunner.java
- 18 comprehensive test cases (no JUnit dependency)
- Tests single and multiple producer/consumer scenarios
- Verifies thread safety, data integrity, and performance
- Includes demo mode for visualization

## ⚡ Performance Notes

- **Small queue (1-5)**: More blocking, slower execution, stress tests wait/notify
- **Medium queue (10-20)**: Balanced performance, recommended for most cases
- **Large queue (50+)**: Minimal blocking, faster execution, higher memory usage

Typical execution times (varies by system):
- Basic tests: 2-4 minutes
- Multi-threaded tests: 30-60 seconds
- **Total suite: 3-5 minutes**

## ✅ Success Criteria

All 18 tests verify:
- ✅ No race conditions
- ✅ No deadlocks
- ✅ All items transferred correctly
- ✅ Order preserved (single producer scenarios)
- ✅ No duplicate items
- ✅ Threads terminate cleanly
- ✅ Queue empty after completion

## 🐛 Troubleshooting

**Tests timeout or run slowly:**
- Normal behavior for stress tests (500 items)
- Ensure sufficient system resources
- Check Java heap size if needed

**ClassNotFoundException:**
```bash
mvn clean compile
```

**Build fails:**
```bash
# Verify Java version
java -version  # Should be 11+

# Clean and rebuild
mvn clean compile
```

## 📝 License

Educational implementation for assignment submission.

## 👤 Author

Assignment 1 - Producer-Consumer Pattern Implementation
