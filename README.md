# Build Challenge - Java Programming Assignments

[![Java](https://img.shields.io/badge/Java-11%2B%20%7C%2017%2B-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![Tests](https://img.shields.io/badge/Tests-99%20Passing-brightgreen.svg)](#testing-summary)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A comprehensive collection of Java programming assignments demonstrating **functional programming**, **concurrent programming**, **data analysis**, and **software engineering best practices**.

---

## 📋 Repository Overview

This repository contains two complete assignments showcasing different aspects of Java development:

| Assignment | Focus Area | Key Technologies | Tests | Status |
|------------|-----------|------------------|-------|--------|
| **[Assignment 1](#assignment-1-producer-consumer-pattern)** | Concurrent Programming & Thread Synchronization | Multi-threading, Blocking Queues, Wait/Notify | 18 | ✅ Complete |
| **[Assignment 2](#assignment-2-sales-data-analytics)** | Data Analysis & Functional Programming | Streams API, Lambda Expressions, CSV Processing | 81 | ✅ Complete |

**Total Tests:** 99 | **Pass Rate:** 100% | **Lines of Code:** 3,000+

---

## 🚀 Quick Navigation

- [Assignment 1: Producer-Consumer Pattern](#assignment-1-producer-consumer-pattern)
  - [Overview](#a1-overview)
  - [Features](#a1-features)
  - [Quick Start](#a1-quick-start)
  - [Sample Output](#a1-sample-output)
- [Assignment 2: Sales Data Analytics](#assignment-2-sales-data-analytics)
  - [Overview](#a2-overview)
  - [Features](#a2-features)
  - [Quick Start](#a2-quick-start)
  - [Sample Output](#a2-sample-output)
  - [Documentation](#a2-documentation)
- [Repository Structure](#repository-structure)
- [Prerequisites](#prerequisites)
- [Testing Summary](#testing-summary)
- [Documentation](#documentation)

---

# Assignment 1: Producer-Consumer Pattern

## <a name="a1-overview"></a>🔄 Overview

A Java implementation of the classic **Producer-Consumer pattern** demonstrating thread synchronization, concurrent programming, blocking queues, and wait/notify mechanisms.

**Location:** `Assignment-1/`

### Key Technologies
- **Language:** Java 11+
- **Build Tool:** Maven 3.6+
- **Concurrency:** Native Java threading, BlockingQueue, synchronized blocks

### <a name="a1-features"></a>✨ Features

#### Concurrency Concepts
- ✅ **Thread Synchronization**: `synchronized` blocks and proper locking
- ✅ **Concurrent Programming**: Multiple threads running in parallel
- ✅ **Blocking Queues**: Thread-safe queue operations with capacity management
- ✅ **Wait/Notify Mechanism**: Efficient inter-thread communication
- ✅ **Multiple Producers/Consumers**: Advanced multi-threading scenarios

#### Implementation Highlights
- **Producer Thread**: Reads items from source, adds to shared queue
- **Consumer Thread**: Reads from queue, stores in destination
- **Synchronization**: Proper locking prevents race conditions
- **Blocking Behavior**: Threads wait when queue is full/empty
- **Graceful Shutdown**: Clean thread termination

### <a name="a1-quick-start"></a>🚀 Quick Start

#### Build & Run

```bash
# Navigate to Assignment 1
cd Assignment-1

# Using Maven (Recommended)
mvn clean compile

# Run all 18 tests
mvn exec:java

# Run demo mode
mvn exec:java -Dexec.args="demo"

# Create executable JAR
mvn package
java -jar target/producer-consumer-1.0.0-jar-with-dependencies.jar
```

#### Alternative: Direct Java Compilation
```bash
# Compile
javac -d target/classes src/main/java/com/assignment/producerconsumer/*.java
javac -cp target/classes -d target/test-classes src/test/java/com/assignment/producerconsumer/*.java

# Run tests
java -cp target/classes:target/test-classes com.assignment.producerconsumer.SimpleTestRunner

# Run demo
java -cp target/classes:target/test-classes com.assignment.producerconsumer.SimpleTestRunner demo
```

### <a name="a1-sample-output"></a>📈 Sample Output

#### Demo Mode
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
[CONSUMER] Processed 'Item-1'. Destination size: 1
[PRODUCER] Producing item: Item-2
[PRODUCER] Added 'Item-2' to queue. Queue size: 2
...
============================================================
FINAL RESULTS
============================================================
Items transferred: 10/10
[SUCCESS] All items transferred correctly!
============================================================
```

#### Test Suite
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

[Test 2] Thread Synchronization - No Race Conditions
  [OK] All 100 items transferred safely
  [OK] No data corruption
  [PASS] Test 2 PASSED

[Test 3] Small Queue Capacity (1) - Blocking Behavior
  [OK] All items transferred with queue size 1
  [OK] Wait/notify mechanism working
  [PASS] Test 3 PASSED

...

[Test 16] Multiple Producers (3) → Single Consumer
  [OK] All items from 3 producers consumed
  [OK] No duplicates or lost items
  [PASS] Test 16 PASSED

[Test 17] Single Producer → Multiple Consumers (3)
  [OK] All items distributed across 3 consumers
  [OK] No item consumed twice
  [PASS] Test 17 PASSED

[Test 18] Multiple Producers (2) → Multiple Consumers (2)
  [OK] Complex multi-threading scenario successful
  [OK] All items transferred correctly
  [PASS] Test 18 PASSED

============================================================
TEST SUMMARY
============================================================
Total Tests:  18
[PASS] Passed:    18
[FAIL] Failed:    0
============================================================
*** ALL TESTS PASSED! ***

Testing Objectives Verified:
  ✓ Thread Synchronization
  ✓ Concurrent Programming
  ✓ Blocking Queues
  ✓ Wait/Notify Mechanism
============================================================
```

### 🧪 Testing (18 Tests)

#### Basic Tests (1-15)
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

#### Advanced Tests (16-18)
| Test | Description |
|------|-------------|
| 16 | **Multiple Producers (3) → Single Consumer** |
| 17 | **Single Producer → Multiple Consumers (3)** |
| 18 | **Multiple Producers (2) → Multiple Consumers (2)** |

### 🔧 Implementation Details

#### Producer.java
- Reads items from source container sequentially
- Adds items to shared blocking queue using `offer()`
- Waits when queue is full using `lock.wait()`
- Notifies consumer when items are added
- Signals completion via `AtomicBoolean` flag

#### Consumer.java
- Polls items from shared blocking queue
- Waits when queue is empty using `lock.wait()`
- Stores consumed items in destination container
- Notifies producer when space is available
- Exits when production is complete and queue is empty

#### SimpleTestRunner.java
- 18 comprehensive test cases (no JUnit dependency)
- Tests single and multiple producer/consumer scenarios
- Verifies thread safety, data integrity, and performance
- Includes demo mode for visualization

### ⚡ Performance Notes

- **Small queue (1-5)**: More blocking, slower execution, stress tests wait/notify
- **Medium queue (10-20)**: Balanced performance, recommended for most cases
- **Large queue (50+)**: Minimal blocking, faster execution, higher memory usage

**Typical execution times** (varies by system):
- Basic tests: 2-4 minutes
- Multi-threaded tests: 30-60 seconds
- **Total suite: 3-5 minutes**

### ✅ Success Criteria

All 18 tests verify:
- ✅ No race conditions
- ✅ No deadlocks
- ✅ All items transferred correctly
- ✅ Order preserved (single producer scenarios)
- ✅ No duplicate items
- ✅ Threads terminate cleanly
- ✅ Queue empty after completion

### 📁 Assignment 1 Structure

```
Assignment-1/
├── pom.xml                                  # Maven configuration
├── README.md                                # Detailed documentation
├── src/
│   ├── main/java/com/assignment/producerconsumer/
│   │   ├── Producer.java                   # Producer implementation
│   │   └── Consumer.java                   # Consumer implementation
│   └── test/java/com/assignment/producerconsumer/
│       └── SimpleTestRunner.java           # 18 comprehensive tests
└── target/                                  # Build output
```

---

# Assignment 2: Sales Data Analytics

## <a name="a2-overview"></a>📊 Overview

A comprehensive **sales data analytics application** demonstrating modern Java programming with functional programming paradigms, Java Streams API, and clean architecture principles.

**Location:** `Assignment-2/`

### Key Technologies
- **Language:** Java 17+
- **Build Tool:** Maven 3.6+
- **Libraries:** Apache Commons CSV 1.10.0, JUnit Jupiter 5.10.0
- **Dataset:** 2,823 sales records (2003-2005)

### <a name="a2-features"></a>✨ Features

#### Core Analytics
- 📊 **Financial Metrics**: Total revenue, average order value, quantity analysis
- 📅 **Temporal Analysis**: Revenue trends by year and month
- 🌎 **Geographic Insights**: Revenue breakdown by territory (NA, EMEA, APAC, Japan)
- 🏆 **Top Performers**: Best selling products and highest revenue customers
- 📦 **Operational Metrics**: Order status tracking and deal size distribution
- 🎯 **Advanced Analytics**: High-value order partitioning
- ✅ **Data Quality**: Automated checks for missing values and duplicates

#### Technical Highlights
- ✅ **Functional Programming** with lambda expressions and method references
- ✅ **Stream Operations** for declarative data processing
- ✅ **Supplier Pattern** for reusable data streams
- ✅ **Immutable Data** structures using Java records
- ✅ **Robust CSV Parsing** with Apache Commons CSV
- ✅ **Comprehensive Testing**: 81 unit tests with 100% pass rate
- ✅ **Clean Architecture**: Separation of concerns with layered design

### <a name="a2-quick-start"></a>🚀 Quick Start

#### Build & Run

```bash
# Navigate to Assignment 2
cd Assignment-2

# Build and run tests
mvn clean package

# Run with sample data
java -jar target/buildChallenge-1.0-SNAPSHOT.jar

# Run with custom CSV file
java -jar target/buildChallenge-1.0-SNAPSHOT.jar path/to/your/data.csv

# Run tests only
mvn test
```

#### Expected Output
```
[INFO] BUILD SUCCESS
[INFO] Tests run: 81, Failures: 0, Errors: 0, Skipped: 0
```

### <a name="a2-sample-output"></a>📈 Sample Output

Running the application with the provided sample data (2,823 sales records) produces:

```
=== Total Revenue ===
  $10,032,628.85

=== Total Orders ===
  2823

=== Total Quantity ===
  99067

=== Average Order Value ===
  $3,553.89

=== Revenue by Year ===
  2003 : $3,516,979.54
  2004 : $4,724,162.60
  2005 : $1,791,486.71

=== Revenue by Region ===
  NA   : $3,852,061.39
  EMEA : $4,979,272.41
  Japan: $  455,173.22
  APAC : $  746,121.83

=== Top 10 Products by Revenue ===
  1. Classic Cars      : $3,919,615.66
  2. Vintage Cars      : $1,903,150.84
  3. Motorcycles       : $1,166,388.34
  4. Trucks and Buses  : $1,127,789.84
  5. Planes            : $  975,003.57
  6. Ships             : $  714,437.13
  7. Trains            : $  226,243.47

=== Top 10 Customers by Revenue ===
  1. Euro Shopping Channel        : $912,294.11
  2. Mini Gifts Distributors Ltd. : $654,858.06
  3. Australian Collectors, Co.   : $200,995.41
  4. Muscle Machine Inc           : $197,736.94
  5. La Rochelle Gifts            : $180,124.90

... (13 total analytics operations)
```

### 🔬 Analytics Operations

| Category | Operations | Stream Techniques |
|----------|-----------|-------------------|
| **Aggregation** | Total revenue, orders, quantity, average | `mapToDouble`, `sum`, `count`, `average` |
| **Grouping** | By year, region, status, deal size, month | `groupingBy` + `summingDouble`, `counting` |
| **Ranking** | Top 10 products, top 10 customers | `sorted`, `limit`, `collect` |
| **Partitioning** | High-value vs low-value orders | `partitioningBy` + `counting` |
| **Quality** | Missing values, duplicates | `filter`, `distinct`, custom logic |

### 🎓 Functional Programming Concepts

**Lambda Expressions:**
```java
// Filtering records with null dates
stream.filter(r -> r.orderDate() != null)

// Grouping by year
stream.collect(Collectors.groupingBy(r -> r.orderDate().getYear()))
```

**Method References:**
```java
// Transform to revenue values
stream.mapToDouble(SalesRecord::revenue)

// Extract quantities
stream.mapToLong(SalesRecord::quantityOrdered)
```

**Supplier Pattern:**
```java
// Reusable stream source
Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

// Create multiple independent streams
long count = supplier.get().count();
double total = supplier.get().mapToDouble(SalesRecord::revenue).sum();
```

**Immutability:**
```java
// Using Java 17 records for immutable data
public record SalesRecord(
    String orderNumber,
    LocalDate orderDate,
    String customerName,
    // ... other fields
) { }
```

### 🧪 Testing (81 Tests)

| Test Class | Tests | Coverage |
|------------|-------|----------|
| **DateUtilsTest** | 10 | Date parsing, format handling, edge cases |
| **SalesRecordTest** | 7 | Record creation, revenue calculation |
| **CsvSalesReaderTest** | 9 | CSV parsing, stream supplier, resource management |
| **AnalyticsServiceTest** | 25 | All analytics methods, stream operations |
| **ConsoleReporterTest** | 16 | Output formatting, error handling |
| **AppTest** | 14 | Integration tests, end-to-end scenarios |

**Run Tests:**
```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=AnalyticsServiceTest

# With coverage report
mvn clean test jacoco:report
```

### <a name="a2-documentation"></a>📚 Documentation



### 📁 Assignment 2 Structure

```
Assignment-2/
├── data/
│   └── sales_data_sample.csv          # 2,823 sales records
├── src/
│   ├── main/java/com/example/sales/
│   │   ├── App.java                   # Main entry point
│   │   ├── model/
│   │   │   └── SalesRecord.java       # Immutable record
│   │   ├── reader/
│   │   │   └── CsvSalesReader.java    # CSV parser with Supplier
│   │   ├── service/
│   │   │   └── AnalyticsService.java  # Core analytics
│   │   ├── output/
│   │   │   └── ConsoleReporter.java   # Output formatter
│   │   └── util/
│   │       └── DateUtils.java         # Date utilities
│   └── test/java/                     # 81 unit tests
├── docs/images/                       # Screenshots
├── pom.xml                            # Maven config
├── README.md                          # Detailed documentation
└── DESIGN_CHOICES_AND_ASSUMPTIONS.md  # Technical decisions
```

---

## 📂 Repository Structure

```
buildChallenge/
│
├── README.md                               # This file - Combined overview
├── ASSIGNMENT_DELIVERABLES.md              # Deliverables checklist
├── .gitignore                              # Git ignore rules
│
├── Assignment-1/                           # Producer-Consumer Pattern
│   ├── src/
│   │   ├── main/java/com/assignment/producerconsumer/
│   │   └── test/java/...
│   ├── target/
│   │   └── producer-consumer-1.0.0-jar-with-dependencies.jar
│   ├── pom.xml
│   └── README.md
│
└── Assignment-2/                           # Sales Data Analytics
    ├── data/
    │   └── sales_data_sample.csv
    ├── src/
    │   ├── main/java/com/example/sales/
    │   └── test/java/...
    ├── docs/images/
    ├── target/
    │   └── buildChallenge-1.0-SNAPSHOT.jar
    ├── pom.xml
    ├── README.md
    └── DESIGN_CHOICES_AND_ASSUMPTIONS.md
```

---

## 📋 Prerequisites

### System Requirements
- **Java JDK**: 11+ (Assignment 1) or 17+ (Assignment 2)
- **Apache Maven**: 3.6 or higher
- **Operating System**: Windows, macOS, or Linux
- **Memory**: 4GB RAM minimum (8GB recommended)

### Verify Installation

```bash
# Check Java version
java -version
# Should output: java version "11" or higher

# Check Maven version
mvn -version
# Should output: Apache Maven 3.6.x or higher
```

### Installation Guides
- **Java**: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Maven**: [Apache Maven](https://maven.apache.org/download.cgi)

---

## 🧪 Testing Summary

### Overall Statistics

| Assignment | Tests | Pass | Fail | Pass Rate | Execution Time |
|------------|-------|------|------|-----------|----------------|
| **Assignment 1** | 18 | 18 | 0 | 100% | ~3-5 minutes |
| **Assignment 2** | 81 | 81 | 0 | 100% | ~1.4 seconds |
| **TOTAL** | **99** | **99** | **0** | **100%** | ~5 minutes |

### Run All Tests

```bash
# Assignment 1 Tests
cd Assignment-1
mvn test

# Assignment 2 Tests
cd ../Assignment-2
mvn exec:java

# Or run individually
cd Assignment-2
java -cp target/classes:target/test-classes com.assignment.producerconsumer.SimpleTestRunner
```

### Test Coverage Highlights

**Assignment 1:**
- Basic producer-consumer scenarios
- Thread synchronization verification
- Race condition detection
- Blocking queue behavior tests
- Multi-producer/multi-consumer scenarios
- Stress tests (500 items)
- Graceful shutdown verification

**Assignment 2:**
- Unit tests for all analytics methods
- Edge case handling (null, empty, invalid data)
- Integration tests for end-to-end workflows
- Stream operation verification
- Functional programming pattern tests

---


### Key Documentation Sections

**Assignment 2 Design Documentation includes:**
1. Programming Language Selection
2. Dataset Selection
3. Architectural Design
4. Data Model Design
5. Functional Programming Approach
6. Technology Stack Choices
7. CSV Format Assumptions
8. Analytics Operations Design
9. Error Handling Strategy
10. Testing Strategy
11. Performance Considerations
12. User Experience Design
13. Build and Deployment
14. Code Quality Standards
15. Assumptions Summary
16. Lessons Learned & Reflections

---

## 🎯 Learning Outcomes

### Technical Skills Demonstrated

**Java Fundamentals:**
- ✅ Core Java 11+ and Java 17+ features
- ✅ Object-oriented programming
- ✅ Generics and type safety
- ✅ Exception handling
- ✅ Resource management

**Concurrent Programming (Assignment 1):**
- ✅ Multi-threading and thread lifecycle
- ✅ Synchronization and locks
- ✅ Wait/notify mechanism
- ✅ BlockingQueue operations
- ✅ Race condition prevention
- ✅ Deadlock avoidance

**Functional Programming (Assignment 2):**
- ✅ Lambda expressions and method references
- ✅ Stream API (map, filter, reduce, collect)
- ✅ Collectors (groupingBy, partitioningBy, summingDouble)
- ✅ Supplier pattern and function composition
- ✅ Immutability with records

**Software Engineering:**
- ✅ Clean architecture and layered design
- ✅ Separation of concerns
- ✅ Dependency injection
- ✅ Design patterns (Supplier, Producer-Consumer)
- ✅ Unit testing and test-driven development
- ✅ Build automation with Maven
- ✅ Version control with Git

**Data Analysis (Assignment 2):**
- ✅ CSV parsing and data ingestion
- ✅ Data aggregation and grouping
- ✅ Statistical calculations
- ✅ Top-N analysis and ranking
- ✅ Data quality checks
- ✅ Temporal and geographic analysis

---


## 🚀 Getting Started

### Clone and Build

```bash
# Clone the repository
git clone https://github.com/yourusername/buildChallenge.git
cd buildChallenge

# Build Assignment 1
cd Assignment-1
mvn clean package
java -jar target/buildChallenge-1.0-SNAPSHOT.jar

# Build Assignment 2
cd ../Assignment-2
mvn clean compile
mvn exec:java
```
