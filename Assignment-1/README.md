# Sales Data Analytics - Java Streams & Functional Programming

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Tests](https://img.shields.io/badge/Tests-81%20Passing-brightgreen.svg)](#testing)

A comprehensive sales data analytics application demonstrating modern Java programming with functional programming paradigms, Java Streams API, and best practices in software development.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Sample Output](#sample-output)
- [Analytics Operations](#analytics-operations)
- [Testing](#testing)
- [Functional Programming Concepts](#functional-programming-concepts)
- [CSV Format](#csv-format)
- [Contributing](#contributing)

## 🎯 Overview

This project analyzes sales data from CSV files using Java 17+ features and demonstrates proficiency with:

- **Functional Programming**: Lambda expressions, method references, pure functions
- **Java Streams API**: map, filter, reduce, sorted, limit, collect operations
- **Data Aggregation**: groupingBy, partitioningBy, summingDouble, counting collectors
- **Modern Java**: Records, try-with-resources, Optional handling
- **Clean Architecture**: Separation of concerns with layered design
- **Comprehensive Testing**: 81 unit tests with 100% pass rate

## ✨ Features

### Core Analytics
- 📊 **Financial Metrics**: Total revenue, average order value, quantity analysis
- 📅 **Temporal Analysis**: Revenue trends by year and month
- 🌎 **Geographic Insights**: Revenue breakdown by territory (NA, EMEA, APAC, Japan)
- 🏆 **Top Performers**: Best selling products and highest revenue customers
- 📦 **Operational Metrics**: Order status tracking and deal size distribution
- 🎯 **Advanced Analytics**: High-value order partitioning
- ✅ **Data Quality**: Automated checks for missing values and duplicates

### Technical Highlights
- ✅ Functional programming with lambda expressions and method references
- ✅ Stream operations for declarative data processing
- ✅ Supplier pattern for reusable data streams
- ✅ Immutable data structures using Java records
- ✅ Robust CSV parsing with Apache Commons CSV
- ✅ Comprehensive error handling
- ✅ Resource management with try-with-resources
- ✅ 81 unit tests ensuring code quality

## 🛠 Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17+ | Core programming language |
| **Maven** | 3.6+ | Build and dependency management |
| **Apache Commons CSV** | 1.10.0 | CSV parsing and processing |
| **JUnit Jupiter** | 5.10.0 | Unit testing framework |
| **Maven Shade Plugin** | 3.5.0 | Creating executable JAR |

## 📁 Project Structure

```
buildChallenge/
├── data/
│   └── sales_data_sample.csv        # Sample sales data (2823 records)
├── src/
│   ├── main/
│   │   └── java/com/example/sales/
│   │       ├── App.java              # Main application entry point
│   │       ├── model/
│   │       │   └── SalesRecord.java  # Immutable record for sales data
│   │       ├── reader/
│   │       │   └── CsvSalesReader.java  # CSV parsing with Supplier pattern
│   │       ├── service/
│   │       │   └── AnalyticsService.java  # Core analytics with Streams
│   │       ├── output/
│   │       │   └── ConsoleReporter.java  # Console output formatter
│   │       └── util/
│   │           └── DateUtils.java    # Date parsing utilities
│   └── test/
│       ├── java/                     # 81 comprehensive unit tests
│       └── resources/                # Test data files
├── pom.xml                           # Maven configuration
├── README.md                         # This file
└── .gitignore                        # Git ignore rules
```

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java Development Kit (JDK)** 17 or higher
  - Check version: `java -version`
  - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

- **Apache Maven** 3.6 or higher
  - Check version: `mvn -version`
  - Download: [Maven](https://maven.apache.org/download.cgi)

## 🚀 Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/buildChallenge.git
cd buildChallenge
```

### Step 2: Build the Project

```bash
mvn clean package
```

This command will:
- Download all dependencies
- Compile the source code
- Run all 81 unit tests
- Create an executable JAR file in `target/` directory

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Tests run: 81, Failures: 0, Errors: 0, Skipped: 0
```

## 💻 Usage

### Run with Default Data

```bash
java -jar target/buildChallenge-1.0-SNAPSHOT.jar
```

This uses the sample data file: `data/sales_data_sample.csv`

### Run with Custom CSV File

```bash
java -jar target/buildChallenge-1.0-SNAPSHOT.jar path/to/your/data.csv
```

### Run Tests Only

```bash
mvn test
```

### Run Tests with Details

```bash
mvn test -Dtest=AnalyticsServiceTest
```

## 📊 Sample Output

Running the application with the provided sample data (2,823 sales records) produces:

### Console Output Screenshots

![Sample Output - Part 1](docs/images/output-part1.png)
*Complete analytics output showing financial metrics, regional analysis, and top performers*

![Sample Output - Part 2](docs/images/output-part2.png)
*Monthly revenue trends and data quality checks*

### Detailed Output

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
  2003 : 3516979.54
  2004 : 4724162.6
  2005 : 1791486.71

=== Revenue by Region ===
  NA : 3852061.39
  EMEA : 4979272.41
  Japan : 455173.22
  APAC : 746121.83

=== Top 10 Products by Revenue ===
  Classic Cars : 3919615.66
  Vintage Cars : 1903150.84
  Motorcycles : 1166388.34
  Trucks and Buses : 1127789.84
  Planes : 975003.57
  Ships : 714437.13
  Trains : 226243.47

=== Top 10 Customers by Revenue ===
  Euro Shopping Channel : 912294.11
  Mini Gifts Distributors Ltd. : 654858.06
  Australian Collectors, Co. : 200995.41
  Muscle Machine Inc : 197736.94
  La Rochelle Gifts : 180124.9
  Dragon Souveniers, Ltd. : 172989.68
  Land of Toys Inc. : 164069.44
  The Sharp Gifts Warehouse : 160010.27
  AV Stores, Co. : 157807.81
  Anna's Decorations, Ltd : 153996.13

=== Orders by Status ===
  In Process : 41
  On Hold : 44
  Resolved : 47
  Shipped : 2617
  Cancelled : 60
  Disputed : 14

=== Orders by DealSize ===
  Small : 1282
  Medium : 1384
  Large : 157

=== Partition High Value Orders (> 500.0) ===
  false : 1
  true : 2822

=== Monthly Revenue (YYYY-MM) ===
  2003-01 : 129753.6
  2003-02 : 140836.19
  2003-03 : 174504.9
  2003-04 : 201609.55
  2003-05 : 192673.11
  2003-06 : 168082.56
  2003-07 : 187731.88
  2003-08 : 197809.3
  2003-09 : 263973.36
  2003-10 : 568290.97
  2003-11 : 1029837.66
  2003-12 : 261876.46
  2004-01 : 316577.42
  2004-02 : 311419.53
  2004-03 : 205733.73
  2004-04 : 206148.12
  2004-05 : 273438.39
  2004-06 : 286674.22
  2004-07 : 327144.09
  2004-08 : 461501.27
  2004-09 : 320750.91
  2004-10 : 552924.25
  2004-11 : 1089048.01
  2004-12 : 372802.66
  2005-01 : 339543.42
  2005-02 : 358186.18
  2005-03 : 374262.76
  2005-04 : 261633.29
  2005-05 : 457861.06

=== Data Quality Checks ===
  rows : 2823
  missingOrderDate : 0
  missingSalesValue : 0
  duplicateOrderNumbers : 2516
```

## 🔬 Analytics Operations

### 1. Basic Financial Metrics

| Operation | Method | Description |
|-----------|--------|-------------|
| **Total Revenue** | `totalRevenue()` | Sum of all sales amounts |
| **Total Orders** | `totalOrders()` | Count of all orders |
| **Total Quantity** | `totalQuantity()` | Sum of quantities ordered |
| **Average Order Value** | `averageOrderValue()` | Mean revenue per order |

### 2. Grouping & Aggregation

| Operation | Method | Collector Used |
|-----------|--------|----------------|
| **Revenue by Year** | `revenueByYear()` | `groupingBy` + `summingDouble` |
| **Revenue by Region** | `revenueByRegion()` | `groupingBy` + `summingDouble` |
| **Orders by Status** | `ordersByStatus()` | `groupingBy` + `counting` |
| **Orders by Deal Size** | `ordersByDealSize()` | `groupingBy` + `counting` |
| **Monthly Revenue** | `monthlyRevenue()` | `groupingBy` + `summingDouble` |

### 3. Top Performers

| Operation | Method | Stream Operations |
|-----------|--------|-------------------|
| **Top Products** | `topProducts(n)` | `groupingBy` → `sorted` → `limit` |
| **Top Customers** | `topCustomers(n)` | `groupingBy` → `sorted` → `limit` |

### 4. Advanced Analytics

| Operation | Method | Collector Used |
|-----------|--------|----------------|
| **High-Value Partition** | `partitionHighValue(threshold)` | `partitioningBy` + `counting` |
| **Data Quality Checks** | `dataQualityChecks()` | Multiple stream operations |

## 🧪 Testing

### Test Coverage

```
Total Tests: 81
Pass Rate: 100%
Execution Time: ~1.4 seconds
```

### Test Breakdown

| Test Class | Tests | Coverage |
|------------|-------|----------|
| **DateUtilsTest** | 10 | Date parsing, format handling, edge cases |
| **SalesRecordTest** | 7 | Record creation, revenue calculation |
| **CsvSalesReaderTest** | 9 | CSV parsing, stream supplier, resource management |
| **AnalyticsServiceTest** | 25 | All analytics methods, stream operations, functional programming |
| **ConsoleReporterTest** | 16 | Output formatting, error handling |
| **AppTest** | 14 | Integration tests, end-to-end scenarios |

### Run Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=AnalyticsServiceTest

# With coverage report
mvn clean test jacoco:report
```

## 🎓 Functional Programming Concepts

This project demonstrates key functional programming principles:

### 1. Lambda Expressions

```java
// Filtering records with null dates
stream.filter(r -> r.orderDate() != null)

// Grouping by year
stream.collect(Collectors.groupingBy(r -> r.orderDate().getYear()))
```

### 2. Method References

```java
// Transform to revenue values
stream.mapToDouble(SalesRecord::revenue)

// Extract quantities
stream.mapToLong(SalesRecord::quantityOrdered)
```

### 3. Stream Operations

| Operation | Usage in Project |
|-----------|------------------|
| **map** | Transform records to values |
| **filter** | Remove invalid data (null dates) |
| **reduce / sum** | Calculate totals |
| **sorted** | Order top products/customers |
| **limit** | Restrict to top N results |
| **collect** | Aggregate into collections |

### 4. Collectors

| Collector | Usage in Project |
|-----------|------------------|
| **groupingBy** | Group by year, region, product, customer |
| **partitioningBy** | Split high/low value orders |
| **summingDouble** | Sum revenue values |
| **counting** | Count orders by category |
| **toList / toMap** | Collect results |

### 5. Supplier Pattern

```java
// Reusable stream source
Supplier<Stream<SalesRecord>> supplier = CsvSalesReader.streamSupplier(csvPath);

// Create multiple independent streams
long count = supplier.get().count();
double total = supplier.get().mapToDouble(SalesRecord::revenue).sum();
```

### 6. Immutability

```java
// Using Java 17 records for immutable data
public record SalesRecord(
    String orderNumber,
    LocalDate orderDate,
    // ... other fields
) { }
```

## 📄 CSV Format

Your CSV file must contain these columns (case-sensitive headers):

### Required Columns

| Column | Type | Description | Example |
|--------|------|-------------|---------|
| **ORDERNUMBER** | String | Unique order identifier | "10001" |
| **ORDERDATE** | Date | Order date (multiple formats supported) | "2/24/2003 0:00" |
| **CUSTOMERNAME** | String | Customer name | "Acme Corp" |
| **STATUS** | String | Order status | "Shipped" |
| **QUANTITYORDERED** | Integer | Quantity of items | 25 |
| **PRICEEACH** | Double | Price per item | 95.70 |
| **SALES** | Double | Total sales amount | 2392.50 |

### Optional Columns

| Column | Type | Description |
|--------|------|-------------|
| PRODUCTLINE | String | Product category |
| PRODUCTCODE | String | Product identifier |
| COUNTRY | String | Customer country |
| CITY | String | Customer city |
| TERRITORY | String | Sales territory (NA, EMEA, APAC, Japan) |
| DEALSIZE | String | Deal classification (Small, Medium, Large) |

### Supported Date Formats

- `M/d/yyyy H:mm` → `2/24/2003 0:00`
- `yyyy-MM-dd` → `2023-06-15`
- `M/d/yyyy` → `12/31/2024`

### Example CSV

```csv
ORDERNUMBER,QUANTITYORDERED,PRICEEACH,SALES,ORDERDATE,STATUS,PRODUCTLINE,CUSTOMERNAME,COUNTRY,TERRITORY,DEALSIZE
10001,25,95.70,2392.50,2/24/2003 0:00,Shipped,Motorcycles,Acme Corp,USA,NA,Small
10002,30,100.00,3000.00,3/15/2003 0:00,Shipped,Classic Cars,Tech Inc,France,EMEA,Medium
```

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

Please ensure:
- All tests pass (`mvn test`)
- Code follows existing style
- New features include tests
- Documentation is updated

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

**Sales Analytics Team**

## 🙏 Acknowledgments

- Built as part of a coding challenge to demonstrate Java proficiency
- Sample dataset provided for educational purposes
- Inspired by real-world sales analytics requirements

## 📞 Support

For questions or issues:
- Open an issue on GitHub
- Contact: [your-email@example.com]

---

**Made with ❤️ using Java 17, Streams API, and Functional Programming**

*Last Updated: November 2024*
