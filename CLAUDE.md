# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

This is the NASA PDS fork of [opencsv](http://opencsv.sf.net), a Java CSV parsing library. It is published as `gov.nasa.pds:opencsv` to Maven Central and inherits from the `gov.nasa.pds:parent` POM. The fork is maintained to allow PDS-specific dependency updates and security patches.

## Build Commands

```bash
# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=CSVParserTest

# Run a single test method
mvn test -Dtest=CSVParserTest#testMethod

# Build and install to local Maven repo
mvn install

# Build without tests
mvn install -DskipTests

# Generate site/docs
mvn clean site
```

CI tests against Java 11 and 17. Source/target compatibility is Java 8 (`maven.compiler.source/target=1.8`).

## Code Quality

Three static analysis tools run as part of the Maven build:

- **Checkstyle** (`checkstyle.xml`) — enforces Javadoc on all package-scope and above methods, visibility modifiers, no magic numbers, and naming conventions.
- **PMD** (`pmd-ruleset.xml`) — enforces best practices including empty catch blocks (priority 2 violation), cyclomatic complexity ≤30/method, and ≤24 methods/class.
- **SpotBugs** — suppressions in `spotbugs-suppressions.xml`.

All public and package-scope methods **require Javadoc**. This is enforced by Checkstyle.

## Architecture

The library has two distinct layers:

### Core Layer (`com.opencsv`)
Low-level CSV tokenization and I/O:
- `ICSVParser` / `AbstractCSVParser` — parser interface and base; two implementations: `CSVParser` (lenient, configurable) and `RFC4180Parser` (strict RFC 4180 compliance)
- `ICSVWriter` / `AbstractCSVWriter` — writer interface and base; `CSVWriter` is the standard implementation, `CSVParserWriter` delegates quoting to a parser instance
- `CSVReader` / `CSVReaderBuilder` — wraps a parser to read lines from a `Reader`; `CSVReaderHeaderAware` adds header-keyed row access
- `ResultSetHelper` / `ResultSetHelperService` — reads JDBC `ResultSet` rows as CSV

### Bean Binding Layer (`com.opencsv.bean`)
Annotation-driven mapping between CSV rows and Java beans:
- **Annotations** — `@CsvBindByName`, `@CsvBindByPosition`, `@CsvBindAndJoinBy*`, `@CsvBindAndSplitBy*`, `@CsvCustomBindBy*`, `@CsvDate`, `@CsvNumber`, `@CsvIgnore`, `@CsvRecurse`
- **Mapping strategies** — `HeaderColumnNameMappingStrategy` (header-based), `ColumnPositionMappingStrategy` (index-based), `FuzzyMappingStrategy` (approximate header matching); all extend `AbstractMappingStrategy`
- **`CsvToBean` / `CsvToBeanBuilder`** — reads CSV into a `Stream<T>` or `List<T>` of beans; supports parallel processing via `bean/concurrent/`
- **`StatefulBeanToCsv` / `StatefulBeanToCsvBuilder`** — writes beans to CSV; stateful to preserve ordering in parallel writes
- **`BeanField` hierarchy** — `BeanFieldSingleValue`, `BeanFieldSplit`, `BeanFieldJoin*` handle single-value, multi-value (split by regex), and multi-column (join) field mappings
- **Converters** — `ConverterDate`, `ConverterNumber`, `ConverterPrimitiveTypes`, `ConverterCurrency`, `ConverterEnum`, `ConverterUUID`; extend `AbstractCsvConverter`
- **`bean/validators/`** — implement `CsvValidator` interface for field-level validation
- **`bean/processor/`** — implement `CsvDataPreProcessor` for field transformation before binding

## Release Process

- **Unstable snapshot**: automatically published on push to `main` via the `unstable-cicd.yaml` workflow using `NASA-PDS/roundup-action@stable`
- **Stable release**: triggered by pushing a tag matching `release/*`; uses `stable-cicd.yaml`
