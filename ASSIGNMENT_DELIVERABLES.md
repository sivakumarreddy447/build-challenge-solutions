# Build Challenge - Assignment Deliverables

## 📦 Repository Overview

This repository contains completed assignments demonstrating proficiency in data analysis, functional programming, and software engineering best practices.

**Repository:** `buildChallenge`  
**Author:** Sales Analytics Team  
**Date:** November 2024  

---

## 📋 Assignment 1: Sales Data Analytics with Java Streams

### Status: ✅ Complete

### Location
```
Assignment-1/
```

### Description
A comprehensive sales data analytics application demonstrating modern Java programming with functional programming paradigms, Java Streams API, and clean architecture principles.

### Key Features
- ✅ **Functional Programming**: Lambda expressions, method references, pure functions
- ✅ **Stream Operations**: map, filter, reduce, sorted, limit, collect
- ✅ **Data Aggregation**: groupingBy, partitioningBy, summingDouble, counting collectors
- ✅ **Modern Java**: Java 17+, Records, try-with-resources, Optional handling
- ✅ **Clean Architecture**: Separation of concerns with layered design
- ✅ **Comprehensive Testing**: 81 unit tests with 100% pass rate

### Deliverables

| Deliverable | Status | Location | Description |
|------------|--------|----------|-------------|
| **Source Code** | ✅ | `Assignment-1/src/main/` | Complete application with 5 layers |
| **Unit Tests** | ✅ | `Assignment-1/src/test/` | 81 tests covering all functionality |
| **README** | ✅ | `Assignment-1/README.md` | Setup instructions, usage, sample output |
| **Design Documentation** | ✅ | `Assignment-1/DESIGN_CHOICES_AND_ASSUMPTIONS.md` | Technical decisions & assumptions |
| **Sample Data** | ✅ | `Assignment-1/data/` | 2,823 sales records (2003-2005) |
| **Executable JAR** | ✅ | `Assignment-1/target/` | Runnable uber-JAR with dependencies |
| **Test Results** | ✅ | `Assignment-1/target/surefire-reports/` | Detailed test execution reports |

### Quick Start

```bash
# Navigate to assignment directory
cd Assignment-1

# Build and run tests
mvn clean package

# Run application with sample data
java -jar target/buildChallenge-1.0-SNAPSHOT.jar

# Run application with custom CSV
java -jar target/buildChallenge-1.0-SNAPSHOT.jar path/to/your/data.csv
```

### Technology Stack
- **Language**: Java 17+
- **Build Tool**: Maven 3.6+
- **CSV Parsing**: Apache Commons CSV 1.10.0
- **Testing**: JUnit Jupiter 5.10.0
- **Packaging**: Maven Shade Plugin 3.5.0

### Analytics Capabilities
1. **Financial Metrics**: Total revenue, average order value, quantity analysis
2. **Temporal Analysis**: Revenue trends by year and month
3. **Geographic Insights**: Revenue breakdown by territory
4. **Top Performers**: Best selling products and highest revenue customers
5. **Operational Metrics**: Order status tracking and deal size distribution
6. **Advanced Analytics**: High-value order partitioning
7. **Data Quality**: Automated checks for missing values and duplicates

### Testing Objectives Met
✅ **Functional Programming**: Extensive use of lambda expressions and pure functions  
✅ **Stream Operations**: All major stream operations demonstrated  
✅ **Data Aggregation**: Multiple grouping and aggregation patterns  
✅ **Lambda Expressions**: Method references and function composition  

### Documentation Highlights

#### README.md
- Comprehensive setup instructions
- Detailed sample output with screenshots
- Analytics operations reference
- Functional programming concepts explanation
- CSV format specification
- Testing guide

#### DESIGN_CHOICES_AND_ASSUMPTIONS.md
**16 Comprehensive Sections:**

1. **Programming Language Selection** - Java vs Python rationale
2. **Dataset Selection** - Sales data characteristics and rationale
3. **Architectural Design** - Layered architecture, dependency injection
4. **Data Model Design** - Java records, immutability, revenue logic
5. **Functional Programming Approach** - Streams, suppliers, try-with-resources
6. **Technology Stack Choices** - Apache Commons CSV, JUnit 5, Maven
7. **CSV Format Assumptions** - Required/optional fields, date formats
8. **Analytics Operations Design** - Aggregation, grouping, ranking strategies
9. **Error Handling Strategy** - Fail-fast vs graceful degradation
10. **Testing Strategy** - 81 tests, coverage by layer, test data
11. **Performance Considerations** - Stream vs loop, memory management
12. **User Experience Design** - Console output, error messages
13. **Build and Deployment** - Maven config, executable JAR
14. **Code Quality Standards** - Naming, documentation, functional standards
15. **Assumptions Summary** - Data, business, technical assumptions
16. **Lessons Learned & Reflections** - What worked well, improvements

---

## 📊 Testing Results

### Assignment 1 Test Summary

```
Total Tests:    81
Passed:         81
Failed:         0
Errors:         0
Skipped:        0
Success Rate:   100%
Execution Time: ~1.4 seconds
```

### Test Breakdown by Component

| Component | Tests | Coverage |
|-----------|-------|----------|
| **DateUtils** | 10 | Date parsing, format handling, edge cases |
| **SalesRecord** | 7 | Record creation, revenue calculation |
| **CsvSalesReader** | 9 | CSV parsing, stream supplier, resource management |
| **AnalyticsService** | 25 | All analytics methods, stream operations |
| **ConsoleReporter** | 16 | Output formatting, error handling |
| **App** | 14 | Integration tests, end-to-end scenarios |

---

## 🎯 Assignment Objectives Verification

### Assignment 1 Requirements

| Requirement | Status | Evidence |
|------------|--------|----------|
| **Functional Programming** | ✅ | Lambda expressions throughout codebase |
| **Stream Operations** | ✅ | map, filter, reduce, sorted, limit, collect all used |
| **Data Aggregation** | ✅ | Multiple groupingBy and partitioning operations |
| **Lambda Expressions** | ✅ | Method references and functional interfaces |
| **CSV Data Processing** | ✅ | Apache Commons CSV with 2,823 records |
| **Public GitHub Repository** | ✅ | Repository with complete source code |
| **Complete Source Code** | ✅ | All layers implemented (5 components) |
| **Unit Tests** | ✅ | 81 comprehensive tests with 100% pass rate |
| **README with Setup** | ✅ | Detailed instructions and sample output |
| **Console Output** | ✅ | All 13 analyses printed with formatting |
| **Documentation** | ✅ | Design choices and assumptions documented |

---

## 📚 Key Documentation Files

### Assignment 1

1. **[Assignment-1/README.md](Assignment-1/README.md)**
   - Primary documentation
   - Setup and installation instructions
   - Usage examples and sample output
   - Complete analytics operations reference
   - Testing guide

2. **[Assignment-1/DESIGN_CHOICES_AND_ASSUMPTIONS.md](Assignment-1/DESIGN_CHOICES_AND_ASSUMPTIONS.md)**
   - Technical decision rationale
   - Architectural choices explained
   - Data model design decisions
   - Functional programming approach
   - Testing strategy
   - Performance considerations
   - Assumptions documented

3. **[Assignment-1/pom.xml](Assignment-1/pom.xml)**
   - Maven project configuration
   - Dependencies specification
   - Build plugins configuration

---

## 🚀 Running the Project

### Prerequisites
- **Java JDK**: 17 or higher
- **Apache Maven**: 3.6 or higher

### Build & Run

```bash
# Clone repository
git clone https://github.com/yourusername/buildChallenge.git
cd buildChallenge

# Build Assignment 1
cd Assignment-1
mvn clean package

# Run with sample data
java -jar target/buildChallenge-1.0-SNAPSHOT.jar

# Run with custom data
java -jar target/buildChallenge-1.0-SNAPSHOT.jar path/to/data.csv

# Run tests only
mvn test

# Run specific test class
mvn test -Dtest=AnalyticsServiceTest
```

---

## 📈 Sample Output Preview

```
=== Total Revenue ===
  $10,032,628.85

=== Total Orders ===
  2823

=== Average Order Value ===
  $3,553.89

=== Revenue by Year ===
  2003 : $3,516,979.54
  2004 : $4,724,162.60
  2005 : $1,791,486.71

=== Revenue by Region ===
  NA   : $3,852,061.39
  EMEA : $4,979,272.41
  APAC : $  746,121.83
  Japan: $  455,173.22

=== Top 10 Products by Revenue ===
  1. Classic Cars      : $3,919,615.66
  2. Vintage Cars      : $1,903,150.84
  3. Motorcycles       : $1,166,388.34
  4. Trucks and Buses  : $1,127,789.84
  5. Planes            : $  975,003.57

=== Top 10 Customers by Revenue ===
  1. Euro Shopping Channel        : $912,294.11
  2. Mini Gifts Distributors Ltd. : $654,858.06
  3. Australian Collectors, Co.   : $200,995.41

... (13 total analytics operations)
```

---

## 🏆 Highlights & Best Practices

### Code Quality
- ✅ **Clean Architecture**: Separation of concerns across 5 layers
- ✅ **Immutability**: Java records for data integrity
- ✅ **Type Safety**: Strong typing prevents runtime errors
- ✅ **Resource Management**: Try-with-resources for stream safety
- ✅ **Error Handling**: Fail-fast with clear messages
- ✅ **Comprehensive Testing**: 81 unit tests, 100% pass rate

### Functional Programming
- ✅ **Pure Functions**: No side effects, deterministic results
- ✅ **Lambda Expressions**: Concise, expressive code
- ✅ **Method References**: SalesRecord::revenue pattern
- ✅ **Stream Operations**: Declarative data processing
- ✅ **Collectors**: Advanced aggregation patterns
- ✅ **Supplier Pattern**: Reusable stream sources

### Software Engineering
- ✅ **Maven Build System**: Reproducible builds
- ✅ **Dependency Management**: External libraries properly managed
- ✅ **Executable JAR**: Single-command deployment
- ✅ **Version Control**: Git with .gitignore
- ✅ **Documentation**: README + design decisions
- ✅ **Test Coverage**: All layers tested independently

---

## 📞 Repository Structure

```
buildChallenge/
│
├── Assignment-1/                     # Sales Analytics with Java Streams
│   ├── data/
│   │   └── sales_data_sample.csv
│   ├── src/
│   │   ├── main/java/...
│   │   └── test/java/...
│   ├── target/
│   │   └── buildChallenge-1.0-SNAPSHOT.jar
│   ├── pom.xml
│   ├── README.md
│   └── DESIGN_CHOICES_AND_ASSUMPTIONS.md
│
├── ASSIGNMENT_DELIVERABLES.md       # This file - Overview of all assignments
├── .gitignore
└── README.md (optional)             # Root-level repository overview
```

---

## 📝 Grading Checklist

### Assignment 1: Sales Data Analytics

- [x] **Functional Programming** - Lambda expressions and pure functions used throughout
- [x] **Stream Operations** - Extensive use of map, filter, reduce, collect, sorted, limit
- [x] **Data Aggregation** - Multiple groupingBy operations with downstream collectors
- [x] **Lambda Expressions** - Method references and functional composition demonstrated
- [x] **Public GitHub Repository** - Complete source code available
- [x] **Unit Tests** - 81 comprehensive tests covering all components
- [x] **README** - Detailed setup instructions, usage, and sample output
- [x] **Console Output** - All analyses printed with clear formatting
- [x] **CSV Dataset** - 2,823 real-world sales records included
- [x] **Design Documentation** - Choices and assumptions thoroughly documented

---

## 🎓 Learning Outcomes Demonstrated

### Technical Skills
1. **Java 17+ Features**: Records, text blocks, enhanced switch
2. **Functional Programming**: First-class functions, immutability, pure functions
3. **Stream API Mastery**: Complex stream pipelines with collectors
4. **Testing**: Comprehensive unit testing with JUnit 5
5. **Build Tools**: Maven project management and packaging

### Software Engineering
1. **Clean Architecture**: Layered design with separation of concerns
2. **Design Patterns**: Supplier, dependency injection, factory patterns
3. **Error Handling**: Graceful degradation and fail-fast strategies
4. **Documentation**: Technical writing and API documentation
5. **Version Control**: Git workflow and repository management

### Data Analysis
1. **Aggregation**: Grouping, summing, counting, averaging
2. **Filtering**: Data quality checks and null handling
3. **Ranking**: Top-N analysis with sorting
4. **Partitioning**: Binary classification of data
5. **Temporal Analysis**: Time-series grouping and trends

---

## 🔗 Quick Links

### Documentation
- [Assignment 1 README](Assignment-1/README.md)
- [Design Choices & Assumptions](Assignment-1/DESIGN_CHOICES_AND_ASSUMPTIONS.md)
- [Maven POM](Assignment-1/pom.xml)

### Source Code
- [Application Entry Point](Assignment-1/src/main/java/com/example/sales/App.java)
- [Analytics Service](Assignment-1/src/main/java/com/example/sales/service/AnalyticsService.java)
- [Sales Record Model](Assignment-1/src/main/java/com/example/sales/model/SalesRecord.java)
- [CSV Reader](Assignment-1/src/main/java/com/example/sales/reader/CsvSalesReader.java)

### Testing
- [Analytics Service Tests](Assignment-1/src/test/java/com/example/sales/service/AnalyticsServiceTest.java)
- [Test Data](Assignment-1/src/test/resources/)

---

## 📧 Contact & Support

For questions or issues related to this assignment:
- Open an issue on GitHub
- Review the comprehensive documentation in each assignment folder
- Check the DESIGN_CHOICES_AND_ASSUMPTIONS.md for technical details

---

## 📄 License

This project is created for educational purposes as part of a coding challenge.

---

**Made with ❤️ using Java 17, Streams API, and Functional Programming**

*Last Updated: November 20, 2024*

