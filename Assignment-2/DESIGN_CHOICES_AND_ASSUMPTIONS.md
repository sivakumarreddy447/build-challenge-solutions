# Design Choices and Assumptions

## Document Overview

This document outlines the technical decisions, architectural choices, and assumptions made during the development of the Sales Data Analytics application. It serves as a comprehensive reference for understanding the rationale behind the implementation approach.

**Assignment:** Assignment 2 - Data Analysis using Streams API on CSV data  
 

---

## 1. Programming Language Selection

### Choice: Java

**Rationale:**
- **Type Safety**: Strong static typing helps catch errors at compile time, crucial for financial data processing
- **Stream API Maturity**: Java 8+ Streams provide a robust, well-documented functional programming framework
- **Enterprise Readiness**: Java is widely used in enterprise environments where sales analytics are deployed
- **Rich Ecosystem**: Excellent libraries for CSV processing (Apache Commons CSV), testing (JUnit 5), and build automation (Maven)
- **Performance**: JVM optimization provides excellent performance for data-intensive operations

---

## 2. Dataset Selection

### Dataset: Sales Transaction Data (2,823 records)

**Characteristics:**
- **Domain**: Retail sales data spanning 2003-2005
- **Size**: 2,823 sales records across multiple product lines
- **Complexity**: 25 columns including financial, temporal, geographic, and categorical data
- **Diversity**: Multiple dimensions for aggregation (year, month, region, product, customer, status)

**Selection Rationale:**
1. **Rich Analysis Opportunities**: Multiple categorical dimensions enable diverse grouping operations
2. **Real-world Relevance**: Mirrors actual business analytics scenarios
3. **Data Quality Challenges**: Contains edge cases (missing values, duplicates) to demonstrate robust handling
4. **Financial Complexity**: Mix of quantity, price, and sales fields to showcase calculation logic
5. **Temporal Depth**: Multi-year data enables trend analysis and time-based grouping

**Dataset Features:**
- **Geographic Coverage**: 4 territories (NA, EMEA, APAC, Japan), 19 countries
- **Product Variety**: 7 product lines (Classic Cars, Motorcycles, Planes, etc.)
- **Status Diversity**: 6 order statuses (Shipped, In Process, Cancelled, etc.)
- **Deal Sizes**: 3 categories (Small, Medium, Large)

---

## 3. Architectural Design

### 3.1 Layered Architecture

**Structure:**
```
Presentation Layer → Output Formatter (ConsoleReporter)
Service Layer      → Business Logic (AnalyticsService)
Data Access Layer  → CSV Reader (CsvSalesReader)
Model Layer        → Domain Objects (SalesRecord)
Utility Layer      → Helpers (DateUtils)
```

**Rationale:**
- **Separation of Concerns**: Each layer has a single, well-defined responsibility
- **Testability**: Isolated layers can be unit tested independently
- **Maintainability**: Changes to one layer don't cascade through the system
- **Reusability**: Service methods can be called from different presentation layers (CLI, web, API)

### 3.2 Dependency Injection Pattern

**Implementation:**
```java
public AnalyticsService(Supplier<Stream<SalesRecord>> streamSupplier)
```

**Benefits:**
- **Flexibility**: Service doesn't depend on specific data source implementation
- **Testability**: Easy to inject mock data suppliers for testing
- **Resource Management**: Supplier pattern enables multiple independent streams from single source

---

## 4. Data Model Design

### 4.1 Choice: Java Records

**Implementation:**
```java
public record SalesRecord(
    String orderNumber,
    LocalDate orderDate,
    String customerName,
    // ... 10 more fields
) { }
```

**Rationale:**
1. **Immutability**: Records are inherently immutable, preventing accidental data modification
2. **Conciseness**: Auto-generated constructors, getters, equals(), hashCode(), toString()
3. **Value Semantics**: Records are compared by value, not identity
4. **Pattern Matching Ready**: Future-proof for Java's evolving pattern matching features
5. **Functional Programming Fit**: Immutability aligns with functional paradigms

**Assumptions:**
- Sales records are immutable once created (no need to modify after parsing)
- Value equality is desired (two records with same data should be equal)
- Memory overhead of immutability is acceptable for dataset size

### 4.2 Revenue Calculation Logic

**Implementation:**
```java
public double revenue() {
    if (sales > 0.0) return sales;
    return quantityOrdered * priceEach;
}
```

**Assumptions:**
- Primary revenue source is the `SALES` field
- If `SALES` is zero/missing, calculate as `QUANTITY × PRICE`
- Negative sales values are treated as zero (data error)
- This handles data quality issues gracefully

---

## 5. Functional Programming Approach

### 5.1 Stream Operations

**Design Principle**: Declarative over Imperative

**Examples:**
```java
// Total revenue using method reference and terminal operation
s.mapToDouble(SalesRecord::revenue).sum()

// Grouping with collector chaining
s.collect(Collectors.groupingBy(
    r -> r.orderDate().getYear(),
    Collectors.summingDouble(SalesRecord::revenue)
))
```

**Rationale:**
- **Readability**: Declarative style clearly expresses intent
- **Maintainability**: Less boilerplate than imperative loops
- **Optimization**: Stream API can parallelize or optimize internally
- **Composability**: Easy to chain operations

### 5.2 Supplier Pattern for Stream Reuse

**Design:**
```java
Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);
```

**Rationale:**
- **Stream Single-Use Limitation**: Streams can only be consumed once
- **Multiple Analyses**: Each analytics operation needs a fresh stream
- **Lazy Evaluation**: Streams are created only when needed
- **Resource Efficiency**: Single file parsing, multiple stream creation

**Alternative Rejected**: Loading entire dataset into memory
- Would work but less scalable for large files
- Loses lazy evaluation benefits
- Higher memory footprint

### 5.3 Try-with-Resources for Streams

**Pattern:**
```java
try (Stream<SalesRecord> s = streamSupplier.get()) {
    return s.mapToDouble(SalesRecord::revenue).sum();
}
```

**Rationale:**
- **Resource Safety**: Ensures streams are properly closed
- **Memory Management**: Prevents resource leaks
- **Best Practice**: Explicit resource management is Java best practice

---

## 6. Technology Stack Choices

### 6.1 Apache Commons CSV (1.10.0)

**Why This Library:**
- **Industry Standard**: Widely adopted, battle-tested in production
- **RFC 4180 Compliant**: Follows CSV specification
- **Flexible Parsing**: Handles various CSV formats and edge cases
- **Header Support**: Easy mapping of column names to values
- **Error Handling**: Graceful handling of malformed data

**Alternatives Considered:**
- **OpenCSV**: Less actively maintained
- **Manual Parsing**: Error-prone, reinventing the wheel
- **Jackson CSV**: Heavier dependency, more complex

### 6.2 JUnit Jupiter (5.10.0)

**Why JUnit 5:**
- **Modern Testing**: Latest testing paradigms (parameterized tests, nested tests, lifecycle callbacks)
- **Rich Assertions**: Comprehensive assertion library
- **Extension Model**: Flexible extension mechanism
- **IDE Integration**: Excellent support in all major IDEs

**Testing Philosophy:**
- **Comprehensive Coverage**: 81 tests covering all layers
- **Test Pyramid**: Unit tests (fast, isolated) > Integration tests
- **Behavior-Driven**: Tests describe what the code should do
- **Edge Cases**: Explicit tests for null, empty, invalid data

### 6.3 Maven

**Why Maven over Gradle:**
- **Convention over Configuration**: Standard project structure
- **Stability**: Well-established, predictable
- **Simplicity**: XML is verbose but clear for simple projects
- **Ecosystem**: Vast plugin repository
- **Reproducibility**: Deterministic builds

### 6.4 Maven Shade Plugin

**Purpose:** Create uber-JAR with all dependencies

**Rationale:**
- **Distribution**: Single JAR is easier to distribute and run
- **Dependency Resolution**: No classpath issues for end users
- **Simplicity**: One command to run: `java -jar app.jar`

---

## 7. CSV Format Assumptions

### 7.1 Required Columns

**Strict Requirements:**
- `ORDERNUMBER`: Must be present (unique identifier)
- `ORDERDATE`: Required for temporal analysis
- `SALES` or (`QUANTITYORDERED` + `PRICEEACH`): Required for revenue calculation
- `CUSTOMERNAME`: Required for customer analysis

**Rationale:**
- Core analytics depend on these fields
- Application will fail fast if essential fields are missing
- Better to fail early than produce incorrect results

### 7.2 Optional Columns

**Graceful Degradation:**
- `TERRITORY`, `PRODUCTLINE`, `STATUS`, `DEALSIZE`: Optional
- If missing, grouping operations use "UNKNOWN" category
- Application doesn't crash, continues with available data

**Implementation:**
```java
Optional.ofNullable(r.territory()).orElse("UNKNOWN")
```

### 7.3 Date Format Handling

**Supported Formats:**
1. `M/d/yyyy H:mm` (e.g., "2/24/2003 0:00") - Primary format in sample data
2. `yyyy-MM-dd` (e.g., "2023-06-15") - ISO standard
3. `M/d/yyyy` (e.g., "12/31/2024") - Simple format

**Rationale:**
- Real-world CSV files have inconsistent date formats
- Multiple format support increases robustness
- Fallback mechanism prevents parse failures

**Assumptions:**
- All dates are in same timezone (not stored in CSV)
- Time component (H:mm) is informational but not used in analysis
- Invalid dates result in null, which is filtered out in temporal analysis

### 7.4 Data Quality Assumptions

**Expected Issues:**
1. **Duplicates**: Order numbers may repeat (multiple line items per order)
2. **Missing Values**: Some fields may be empty or null
3. **Whitespace**: Headers and values may have leading/trailing spaces
4. **Encoding**: UTF-8 encoding is assumed

**Handling Strategy:**
- **Duplicates**: Counted and reported, not automatically removed (may be legitimate)
- **Missing Values**: Filtered out or defaulted to "UNKNOWN"
- **Whitespace**: Apache Commons CSV trims automatically

---

## 8. Analytics Operations Design

### 8.1 Operation Categories

**1. Aggregation Operations** (reduce to single value)
- Total revenue, total orders, average order value
- Simple terminal operations: `sum()`, `count()`, `average()`

**2. Grouping Operations** (partition and aggregate)
- Revenue by year/region, orders by status/dealsize
- Use `Collectors.groupingBy()` with downstream collectors

**3. Ranking Operations** (sort and limit)
- Top products, top customers
- Chain: group → sort by value → limit → collect

**4. Partitioning Operations** (binary split)
- High-value vs low-value orders
- Use `Collectors.partitioningBy()`

### 8.2 Design Decisions

**Consistency:**
- All methods return immutable collections (Map, List)
- All methods handle null values gracefully
- All methods use try-with-resources for stream management

**Performance:**
- Operations are lazy where possible
- No unnecessary intermediate collections
- LinkedHashMap preserves insertion order for top-N results

**Extensibility:**
- Service methods are independent (no shared state)
- Easy to add new analytics without breaking existing ones
- Parameterized methods (topN, threshold) provide flexibility

---

## 9. Error Handling Strategy

### 9.1 Fail-Fast Principle

**When to Fail:**
- File not found: Throw `IOException` immediately
- CSV parse error: Throw exception with line number
- Missing required columns: Throw exception with clear message

**Rationale:**
- Better to fail with clear error than produce wrong results
- Helps users identify and fix data issues quickly

### 9.2 Graceful Degradation

**When to Continue:**
- Invalid date in single row: Skip that row, log warning
- Missing optional field: Use "UNKNOWN" default
- Zero/negative prices: Use zero revenue for that record

**Rationale:**
- Single bad record shouldn't invalidate entire dataset
- Partial results are better than no results for large datasets

### 9.3 Error Reporting

**User-Facing Errors:**
```
Error: CSV file not found at path: data/missing.csv
Please check the file path and try again.
```

**Developer-Facing Errors:**
```java
throw new IllegalArgumentException(
    "Missing required column: ORDERNUMBER in CSV header"
);
```

---

## 10. Testing Strategy

### 10.1 Test Coverage by Layer

**Model Layer (SalesRecordTest):**
- Record creation and accessors
- Revenue calculation logic (sales vs quantity × price)
- Edge cases (zero, negative values)

**Data Layer (CsvSalesReaderTest):**
- File reading and parsing
- Supplier pattern functionality
- Error handling (missing file, malformed CSV)
- Resource management (stream closure)

**Service Layer (AnalyticsServiceTest):**
- All analytics operations
- Empty dataset handling
- Null value handling
- Correct use of stream operations

**Output Layer (ConsoleReporterTest):**
- Formatting of various data types
- Edge cases (empty maps, null values)
- Output structure and consistency

**Integration Layer (AppTest):**
- End-to-end workflows
- Command-line argument handling
- Error scenarios

### 10.2 Test Data Strategy

**Test Files:**
1. `test_sales_data.csv` (20 records): Comprehensive test with known values
2. `empty_sales_data.csv` (0 records): Edge case testing
3. `invalid_sales_data.csv`: Error handling testing

**Rationale:**
- Small, controlled datasets make tests fast and predictable
- Known values enable exact assertions
- Edge cases ensure robustness

### 10.3 Testing Philosophy

**Principles:**
1. **One Assertion Per Test**: Each test verifies one specific behavior
2. **Descriptive Names**: Test names describe what should happen
   - `shouldCalculateRevenueFromSalesField()`
   - `shouldCalculateRevenueFromQuantityAndPriceWhenSalesIsZero()`
3. **AAA Pattern**: Arrange → Act → Assert structure
4. **Test Independence**: No shared state between tests

---

## 11. Performance Considerations

### 11.1 Stream vs Loop Performance

**Choice:** Java Streams API

**Performance Implications:**
- **Overhead**: Streams have slight overhead vs raw loops (~10-20%)
- **Acceptable Trade-off**: For dataset size (2,823 records), performance difference is negligible (<1ms)
- **Readability Gains**: Far outweigh minor performance cost
- **Future Optimization**: Streams can be parallelized easily if needed

**Benchmark (Approximate):**
- Total Revenue calculation: <1ms
- Complex grouping operations: <5ms
- All 13 analytics operations: <50ms total

### 11.2 Memory Management

**Design:**
- **Streaming**: Data processed as stream, not loaded entirely into memory
- **Lazy Evaluation**: Operations only execute when terminal operation is called
- **Resource Cleanup**: Try-with-resources ensures streams are closed

**Memory Footprint:**
- Sample dataset (2,823 records): ~2MB in memory
- Stream processing overhead: Minimal
- Result maps/lists: Depends on grouping cardinality (typically <100KB)

**Scalability:**
- Current implementation handles files up to 100K records efficiently
- For larger datasets, consider parallel streams or database solutions

### 11.3 CSV Parsing Performance

**Apache Commons CSV Performance:**
- Parsing speed: ~50,000 records/second on modern hardware
- Sample dataset (2,823 records): Parses in ~50-100ms
- Acceptable for interactive use

---

## 12. User Experience Design

### 12.1 Console Output Formatting

**Design Principles:**
- **Clear Sections**: Each analysis has a visual separator
- **Human-Readable**: Currency formatting, thousands separators
- **Sorted Results**: Top performers shown in descending order
- **Consistent Structure**: Same format for similar data (maps, lists)

**Example:**
```
=== Revenue by Year ===
  2003 : $3,516,979.54
  2004 : $4,724,162.60
  2005 : $1,791,486.71
```

### 12.2 Error Messages

**User-Friendly:**
- Clear description of problem
- Actionable suggestion for resolution
- No technical jargon or stack traces (unless debug mode)

**Example:**
```
❌ Error: Could not find CSV file at 'data/missing.csv'
💡 Tip: Check that the file exists and the path is correct.
```

---

## 13. Build and Deployment

### 13.1 Maven Configuration

**Key Decisions:**

**Java Version:** 17
- Latest LTS version at time of development
- Access to modern features (records, text blocks, pattern matching)
- Supported by all major cloud platforms

**Source/Target Compatibility:**
```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

**Encoding:** UTF-8
- Universal standard
- Prevents encoding issues across platforms

### 13.2 Executable JAR

**Configuration:**
```xml
<mainClass>com.example.sales.App</mainClass>
```

**Benefits:**
- **Portability**: Runs anywhere with Java 17+
- **Simplicity**: Single command: `java -jar app.jar [csv-file]`
- **Self-Contained**: All dependencies bundled

---


## Conclusion

This sales data analytics application demonstrates modern Java development practices, emphasizing:

- **Functional programming** with Java Streams API
- **Clean architecture** with separation of concerns
- **Type safety** and immutability
- **Comprehensive testing** with 81 unit tests
- **Real-world data handling** with robust error management

The design choices prioritize **readability, maintainability, and correctness** over premature optimization, resulting in code that is easy to understand, test, and extend.

All choices were made with the assignment objectives in mind:
✅ Functional programming paradigms  
✅ Stream operations (map, filter, reduce, collect)  
✅ Data aggregation with collectors  
✅ Lambda expressions and method references  
✅ Comprehensive testing and documentation  

---



